package takeoff.logistics_service.msa.company.application.service;

import static takeoff.logistics_service.msa.company.application.exception.CompanyErrorCode.COMPANY_NAME_CONFLICT;
import static takeoff.logistics_service.msa.company.application.exception.CompanyErrorCode.COMPANY_NOT_FOUND;
import static takeoff.logistics_service.msa.company.application.exception.CompanyErrorCode.HUB_NOT_FOUND;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import takeoff.logistics_service.msa.common.domain.UserInfoDto;
import takeoff.logistics_service.msa.company.application.client.HubInternalClient;
import takeoff.logistics_service.msa.company.application.dto.request.PostCompanyRequestDto;
import takeoff.logistics_service.msa.company.application.dto.request.PutCompanyRequestDto;
import takeoff.logistics_service.msa.company.application.dto.request.SearchCompanyRequestDto;
import takeoff.logistics_service.msa.company.application.dto.response.GetCompanyResponseDto;
import takeoff.logistics_service.msa.company.application.dto.response.PaginatedResultDto;
import takeoff.logistics_service.msa.company.application.dto.response.PostCompanyResponseDto;
import takeoff.logistics_service.msa.company.application.dto.response.PutCompanyResponseDto;
import takeoff.logistics_service.msa.company.application.exception.CompanyBusinessException;
import takeoff.logistics_service.msa.company.application.kafka.CompanyEventProducer;
import takeoff.logistics_service.msa.company.application.kafka.dto.KafkaCompanyToDeliveryDto;
import takeoff.logistics_service.msa.company.application.kafka.dto.KafkaCompanyToOrderDto;
import takeoff.logistics_service.msa.company.application.kafka.dto.KafkaDeliveryToCompanyDto;
import takeoff.logistics_service.msa.company.application.kafka.dto.KafkaOrderToCompanyDto;
import takeoff.logistics_service.msa.company.domain.entity.Company;
import takeoff.logistics_service.msa.company.domain.repository.CompanyRepository;

@Slf4j
@Service
@RequiredArgsConstructor
public class CompanyServiceImpl implements CompanyService {

	private final CompanyRepository companyRepository;
	private final HubInternalClient hubInternalClient;
	private final CompanyEventProducer companyEventProducer;

	@Override
	public PostCompanyResponseDto saveCompany(PostCompanyRequestDto requestDto) {
		validateCompanyName(requestDto.companyName());
		validateHubExists(requestDto.hubId());

		Company company = Company.create(requestDto.toCommand());
		return PostCompanyResponseDto.from(companyRepository.save(company));
	}

	private void validateCompanyName(String companyName) {
		if(companyRepository.existsByCompanyName(companyName)){
			throw CompanyBusinessException.from(COMPANY_NAME_CONFLICT);
		}
	}

	private void validateHubExists(UUID hubId) {
		try {
			hubInternalClient.checkHubExists(hubId);
		} catch (Exception e) {
			log.error(e.getMessage());
			throw CompanyBusinessException.from(HUB_NOT_FOUND);
		}
	}

	@Override
	@Transactional
	public PutCompanyResponseDto updateCompany(
		UUID companyId, PutCompanyRequestDto requestDto, UserInfoDto userInfoDto) {

		validateCompanyName(requestDto.companyName());
		validateHubExists(requestDto.hubId());
		Company company = getCompany(companyId).modify(requestDto.toCommand());
		return PutCompanyResponseDto.from(company);
	}

	private Company getCompany(UUID companyId) {
		return companyRepository.findByIdAndDeletedAtIsNull(companyId)
			.orElseThrow(() -> CompanyBusinessException.from(COMPANY_NOT_FOUND));
	}

	//KafkaListener
	@Override
	public void getCompanyHubIdKafka(KafkaOrderToCompanyDto kafkaOrderToCompanyDto) {
		Company company = companyRepository.findByIdAndDeletedAtIsNull(kafkaOrderToCompanyDto.companyId())
			.orElseThrow(() -> CompanyBusinessException.from(COMPANY_NOT_FOUND));

		Company supplier = companyRepository.findByIdAndDeletedAtIsNull(
				kafkaOrderToCompanyDto.supplierId())
			.orElseThrow(() -> CompanyBusinessException.from(COMPANY_NOT_FOUND));

		companyEventProducer.sendToOrder(
			KafkaCompanyToOrderDto.builder()
				.orderId(kafkaOrderToCompanyDto.orderId())
				.toHubId(company.getHubId())
				.fromHubId(supplier.getHubId())
				.build());
	}

	@Override
	public void companyHubIdsKafka(KafkaDeliveryToCompanyDto kafkaDeliveryToCompanyDto) {
		Company company = getCompany(kafkaDeliveryToCompanyDto.companyId());
		Company supplier = getCompany(kafkaDeliveryToCompanyDto.supplierId());

		companyEventProducer.sendToDelivery(KafkaCompanyToDeliveryDto.from(
			kafkaDeliveryToCompanyDto.deliveryId(),
			company.getHubId(),
			supplier.getHubId()
		));
	}

	@Override
	@Transactional(readOnly = true)
	public GetCompanyResponseDto findCompany(UUID companyId, UserInfoDto userInfoDto) {
		return GetCompanyResponseDto.from(getCompany(companyId));
	}

	@Override
	@Transactional
	public void deleteCompany(UUID companyId, UserInfoDto userInfoDto) {
		getCompany(companyId).delete(userInfoDto.userId());
	}

	@Override
	@Transactional(readOnly = true)
	public PaginatedResultDto<GetCompanyResponseDto> searchCompany(
		SearchCompanyRequestDto requestDto, UserInfoDto userInfoDto) {

		return PaginatedResultDto
			.from(companyRepository.search(requestDto.toSearchCriteria()));
	}
}
