package takeoff.logistics_service.msa.company.infrastructure.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import takeoff.logistics_service.msa.company.application.kafka.CompanyEventProducer;
import takeoff.logistics_service.msa.company.application.kafka.dto.KafkaCompanyToDeliveryDto;
import takeoff.logistics_service.msa.company.application.kafka.dto.KafkaCompanyToOrderDto;
import takeoff.logistics_service.msa.company.application.kafka.dto.KafkaDeliveryToCompanyDto;


/**
 * @author : hanjihoon
 * @Date : 2025. 03. 30.
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class CompanyEventKafkaProducer implements CompanyEventProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final String companyToOrderTopicName;
    private final String companyToDeliveryTopicName;

    @Override
    public void sendToOrder(KafkaCompanyToOrderDto kafkaCompanyToOrderDto) {
        kafkaTemplate.send(companyToOrderTopicName, kafkaCompanyToOrderDto);
        log.info(companyToOrderTopicName + ": 이벤트 발행");
    }
    @Override
    public void sendToDelivery(KafkaCompanyToDeliveryDto kafkaCompanyToDeliveryDto) {
        kafkaTemplate.send(companyToDeliveryTopicName, kafkaCompanyToDeliveryDto);
        log.info(companyToDeliveryTopicName + ": 이벤트 발행");
    }
}
