package takeoff.logistics_service.msa.order.infrastructure.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import takeoff.logistics_service.msa.order.application.service.kafka.OrderEventProducer;
import takeoff.logistics_service.msa.order.application.service.kafka.dto.KafkaCompanyDto;
import takeoff.logistics_service.msa.order.application.service.kafka.dto.KafkaToDeliveryDto;

/**
 * @author : hanjihoon
 * @Date : 2025. 03. 30.
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class OrderEventKafkaProducer implements OrderEventProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final String deliveryTopicName;
    private final String companyTopicName;

    @Override
    public void sendToDelivery(KafkaToDeliveryDto kafkaToDeliverydto) {
        kafkaTemplate.send(deliveryTopicName, kafkaToDeliverydto);
        log.info(deliveryTopicName , ": 이벤트 발행");
    }

    @Override
    public void sendToCompany(KafkaCompanyDto dto) {
        kafkaTemplate.send(companyTopicName, dto);
        log.info(companyTopicName + ": 이벤트 발행");
    }

}
