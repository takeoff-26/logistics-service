package takeoff.logisticsservice.msa.delivery.delivery.infrastructure.kafka;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import takeoff.logisticsservice.msa.delivery.delivery.application.dto.kafka.KafkaCompanyToDeliveryDto;
import takeoff.logisticsservice.msa.delivery.delivery.application.dto.kafka.KafkaDeliverySequenceDto;
import takeoff.logisticsservice.msa.delivery.delivery.application.dto.kafka.KafkaDeliveryToCompany;
import takeoff.logisticsservice.msa.delivery.delivery.application.dto.kafka.KafkaOrderUpdateDto;
import takeoff.logisticsservice.msa.delivery.delivery.application.kafka.DeliveryEventProducer;


/**
 * @author : hanjihoon
 * @Date : 2025. 03. 30.
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class DeliveryEventKafkaProducer implements DeliveryEventProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final String deliverySequenceTopicName;
    private final String deliveryToCompanyTopicName;
    private final String deliveryToUpdateOrderTopicName;
    private final String deliveryToDeliveryRouteTopicName;

    @Override
    public void sendToDeliverySequence(KafkaDeliverySequenceDto event) {
        kafkaTemplate.send(deliverySequenceTopicName, event);
        log.info(deliverySequenceTopicName + ": 이벤트 발행");
    }

    @Override
    public void sendToCompany(KafkaDeliveryToCompany kafkaDeliveryToCompany) {
        kafkaTemplate.send(deliveryToCompanyTopicName, kafkaDeliveryToCompany);
        log.info(deliveryToCompanyTopicName + ": 이벤트 발행");
    }

    @Override
    public void sendToOrder(KafkaOrderUpdateDto kafkaOrderUpdateDto) {
        kafkaTemplate.send(deliveryToUpdateOrderTopicName, kafkaOrderUpdateDto);
        log.info(deliveryToCompanyTopicName + ": 이벤트 발행");
    }

    @Override
    public void sendToDeliveryRoute(KafkaCompanyToDeliveryDto kafkaCompanyToDeliveryDto) {
        kafkaTemplate.send(deliveryToDeliveryRouteTopicName, kafkaCompanyToDeliveryDto);
        log.info(deliveryToDeliveryRouteTopicName + ": 이벤트 발행");
    }

}
