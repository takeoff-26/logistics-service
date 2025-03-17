package takeoff.logistics_service.msa.product.product.infrastructure.client;

import feign.FeignException.FeignClientException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import takeoff.logistics_service.msa.common.exception.code.CommonErrorCode;
import takeoff.logistics_service.msa.product.product.application.dto.request.PostStockRequestDto;
import takeoff.logistics_service.msa.product.product.application.dto.response.PostStockResponseDto;
import takeoff.logistics_service.msa.product.product.application.exception.ProductBusinessException;
import takeoff.logistics_service.msa.product.product.application.exception.ProductErrorCode;
import takeoff.logistics_service.msa.product.product.application.service.StockClient;
import takeoff.logistics_service.msa.product.product.infrastructure.client.dto.request.PostStockRequest;

@Component
@RequiredArgsConstructor
public class FeignStockClientImpl implements StockClient {

	private final FeignStockClient feignStockClient;

	@Override
	public PostStockResponseDto saveStock(PostStockRequestDto requestDto) {

		try {
			return feignStockClient.saveStock(PostStockRequest.from(requestDto)).toApplicationDto();
		} catch (FeignClientException e) {
			if (e.status() == HttpStatus.BAD_REQUEST.value()) {
				throw ProductBusinessException.from(ProductErrorCode.INVALID_STOCK_REQUEST);
			} else if (e.status() == HttpStatus.NOT_FOUND.value()) {
				throw ProductBusinessException.from(ProductErrorCode.STOCK_NOT_FOUND);
			} else {
				throw ProductBusinessException.from(CommonErrorCode.BAD_GATEWAY);
			}
		}
	}
}
