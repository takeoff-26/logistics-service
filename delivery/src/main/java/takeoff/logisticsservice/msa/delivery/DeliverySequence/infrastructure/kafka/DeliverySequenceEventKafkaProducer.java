package takeoff.logisticsservice.msa.delivery.DeliverySequence.infrastructure.kafka;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import takeoff.logisticsservice.msa.delivery.DeliverySequence.application.dto.kafka.KafkaDeliverySequenceCompanyIdDto;
import takeoff.logisticsservice.msa.delivery.DeliverySequence.application.kafka.DeliverySequenceEventProducer;


/**
 * @author : hanjihoon
 * @Date : 2025. 03. 30.
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class DeliverySequenceEventKafkaProducer implements DeliverySequenceEventProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final String deliveryToSequenceTopicName;

    @Override
    public void sendToDelivery(KafkaDeliverySequenceCompanyIdDto event) {
        kafkaTemplate.send(deliveryToSequenceTopicName, event);
        log.info(deliveryToSequenceTopicName + ": 이벤트 발행");
    }
}
