package takeoff.logistics_service.msa.company.infrastructure.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import takeoff.logistics_service.msa.company.application.service.CompanyService;
import takeoff.logistics_service.msa.company.infrastructure.kafka.dto.KafkaDeliveryToCompany;
import takeoff.logistics_service.msa.company.infrastructure.kafka.dto.KafkaOrderToCompany;

/**
 * @author : hanjihoon
 * @Date : 2025. 03. 31.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class CompanyKafkaListener {

    private final CompanyService companyService;

    @KafkaListener(
        topics = "company-events",
        containerFactory = "kafkaOrderToCompanyContainerFactory"
    )
    public void handleOrderToCompanyResponse(KafkaOrderToCompany event) {
        log.info("오더 응답 수신: {}", event);
        companyService.getCompanyHubIdKafka(event.toApplication());
    }
    @KafkaListener(
        topics = "delivery-to-company-events",
        containerFactory = "kafkaDeliveryToCompanyContainerFactory"
    )
    public void handleDeliveryToCompanyResponse(KafkaDeliveryToCompany kafkaDeliveryToCompany) {
        log.info("오더 응답 수신: {}", kafkaDeliveryToCompany);
        companyService.companyHubIdsKafka(kafkaDeliveryToCompany.toApplication());
    }


}
