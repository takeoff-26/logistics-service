package takeoff.logistics_service.msa.product.stock.application.service;

import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.PessimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import takeoff.logistics_service.msa.common.domain.UserInfoDto;
import takeoff.logistics_service.msa.common.domain.UserRole;
import takeoff.logistics_service.msa.product.stock.application.dto.PaginatedResultDto;
import takeoff.logistics_service.msa.product.stock.application.dto.request.AbortStockRequestDto;
import takeoff.logistics_service.msa.product.stock.application.dto.request.DecreaseStockRequestDto;
import takeoff.logistics_service.msa.product.stock.application.dto.request.IncreaseStockRequestDto;
import takeoff.logistics_service.msa.product.stock.application.dto.request.PostStockRequestDto;
import takeoff.logistics_service.msa.product.stock.application.dto.request.PrepareStockRequestDto;
import takeoff.logistics_service.msa.product.stock.application.dto.request.SearchStockRequestDto;
import takeoff.logistics_service.msa.product.stock.application.dto.request.StockIdRequestDto;
import takeoff.logistics_service.msa.product.stock.application.dto.request.StockItemRequestDto;
import takeoff.logistics_service.msa.product.stock.application.dto.response.DecreaseStockResponseDto;
import takeoff.logistics_service.msa.product.stock.application.dto.response.GetStockResponseDto;
import takeoff.logistics_service.msa.product.stock.application.dto.response.GetUserResponseDto;
import takeoff.logistics_service.msa.product.stock.application.dto.response.IncreaseStockResponseDto;
import takeoff.logistics_service.msa.product.stock.application.dto.response.PostStockResponseDto;
import takeoff.logistics_service.msa.product.stock.application.exception.StockBusinessException;
import takeoff.logistics_service.msa.product.stock.application.exception.StockErrorCode;
import takeoff.logistics_service.msa.product.stock.domain.entity.Stock;
import takeoff.logistics_service.msa.product.stock.domain.entity.StockId;
import takeoff.logistics_service.msa.product.stock.domain.repository.StockRepository;

@Service
@RequiredArgsConstructor
public class StockServiceImpl implements StockService {

	private final StockRepository stockRepository;
	private final UserClient userClient;

	@Override
	public PostStockResponseDto saveStock(PostStockRequestDto requestDto, UserInfoDto userInfo) {
		validateUserInfo(requestDto, userInfo);
		return PostStockResponseDto.from(
			stockRepository.save(Stock.create(requestDto.toCommand())));
	}

	//유 저 정보를 기반으로 해서 요청을 보내서 받아옵니다.

	private void validateUserInfo(PostStockRequestDto requestDto, UserInfoDto userInfo) {
		if (!userInfo.isValid()) {
			throw StockBusinessException.from(StockErrorCode.INVALID_USER_REQUEST);
		}
		GetUserResponseDto userResponse = userClient.findByUserId(userInfo.userId());
		if (!isAccessible(requestDto, userResponse)) {
			throw StockBusinessException.from(StockErrorCode.ACCESS_DENIED);
		}
	}

	private boolean isAccessible(PostStockRequestDto requestDto, GetUserResponseDto userResponse) {
		return isMaster(userResponse)
			|| isCompanyManager(userResponse)
			|| isValidHubManager(requestDto, userResponse);
	}

	private boolean isMaster(GetUserResponseDto userResponseDto) {
		return userResponseDto.role() == UserRole.MASTER_ADMIN;
	}

	private boolean isCompanyManager(GetUserResponseDto userResponseDto) {
		return userResponseDto.role() == UserRole.COMPANY_MANAGER;
	}

	private boolean isValidHubManager(PostStockRequestDto requestDto,
		GetUserResponseDto userResponseDto) {
		return userResponseDto.role() == UserRole.HUB_MANAGER &&
			userResponseDto.hubId() != null && userResponseDto.hubId()
			.equals(requestDto.stockId().hubId());
	}

	@Override
	@Transactional(readOnly = true)
	public GetStockResponseDto findStock(StockIdRequestDto requestDto) {
		return GetStockResponseDto.from(getStock(StockId.create(requestDto.toCommand())));
	}

	private Stock getStock(StockId stockId) {
		return stockRepository.findByIdAndDeletedAtIsNull(stockId)
			.orElseThrow(() -> StockBusinessException.from(StockErrorCode.STOCK_NOT_FOUND));
	}

	@Override
	@Transactional
	public void delete(StockIdRequestDto requestDto) {
		getStock(StockId.create(requestDto.toCommand())).delete(0L);
	}

	@Override
	@Transactional
	public void prepareStock(PrepareStockRequestDto requestDto) {
		try {
			getSortedStocks(requestDto.stocks())
				.forEach(stockItem ->
					getStockWithLock(stockItem.stockId()).decreaseStock(stockItem.quantity()));
		} catch (PessimisticLockingFailureException e) {
			throw StockBusinessException.from(StockErrorCode.STOCK_LOCK_TIMEOUT);
		}
	}

	private List<StockItemRequestDto> getSortedStocks(List<StockItemRequestDto> stocks) {
		return stocks.stream()
			.sorted(Comparator
				.comparing((StockItemRequestDto item) -> item.stockId().productId())
				.thenComparing(item -> item.stockId().hubId()))
			.toList();
	}

	private Stock getStockWithLock(StockIdRequestDto requestDto) {
		return stockRepository
			.findByIdWithLock(StockId.create(requestDto.toCommand()))
			.orElseThrow(() -> StockBusinessException.from(StockErrorCode.STOCK_NOT_FOUND));
	}

	@Override
	@Transactional
	public void abortStock(AbortStockRequestDto requestDto) {
		try {
			getSortedStocks(requestDto.stocks())
				.forEach(stockItem ->
					getStockWithLock(stockItem.stockId()).increaseStock(stockItem.quantity()));
		} catch (PessimisticLockingFailureException e) {
			throw StockBusinessException.from(StockErrorCode.STOCK_LOCK_TIMEOUT);
		}
	}

	@Override
	@Transactional
	public IncreaseStockResponseDto increaseStock(IncreaseStockRequestDto requestDto) {
		try {
			Stock stock = getStockWithLock(requestDto.stockId());
			stock.increaseStock(requestDto.quantity());
			return IncreaseStockResponseDto.from(stock);
		} catch (PessimisticLockingFailureException e) {
			throw StockBusinessException.from(StockErrorCode.STOCK_LOCK_TIMEOUT);
		}
	}

	@Override
	@Transactional
	public DecreaseStockResponseDto decreaseStock(DecreaseStockRequestDto requestDto) {
		try {
			Stock stock = getStockWithLock(requestDto.stockId());
			stock.decreaseStock(requestDto.quantity());
			return DecreaseStockResponseDto.from(stock);
		} catch (PessimisticLockingFailureException e) {
			throw StockBusinessException.from(StockErrorCode.STOCK_LOCK_TIMEOUT);
		}
	}

	@Override
	@Transactional(readOnly = true)
	public PaginatedResultDto<GetStockResponseDto> searchStock(
		SearchStockRequestDto requestDto) {
		return PaginatedResultDto.from(stockRepository.search(requestDto.toSearchCriteria()));
	}

	@Override
	@Transactional
	public void deleteAllByProductId(UUID productId) {
		stockRepository.findAllById_ProductIdAndDeletedAtIsNull(productId)
			.forEach(stock -> stock.delete(0L));
	}

	@Override
	@Transactional
	public void deleteAllByHubId(UUID hubId) {
		stockRepository.findAllById_HubIdAndDeletedAtIsNull(hubId)
			.forEach(stock -> stock.delete(0L));
	}

	@Override
	@Transactional(readOnly = true)
	public GetStockResponseDto findStockWithProductId(UUID productId) {
		return stockRepository
			.findAllById_ProductIdAndDeletedAtIsNullOrderByQuantityDesc(productId)
			.stream().findFirst().map(GetStockResponseDto::from)
			.orElseThrow(() -> StockBusinessException.from(StockErrorCode.STOCK_NOT_FOUND));
	}
}
