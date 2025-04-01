package takeoff.logisticsservice.msa.delivery.delivery.infrastructure.kafka;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import takeoff.logisticsservice.msa.delivery.delivery.application.dto.kafka.KafkaDeliverySequenceDto;
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

    @Override
    public void sendToDeliverySequence(KafkaDeliverySequenceDto event) {
        kafkaTemplate.send(deliverySequenceTopicName, event);
        log.info(deliverySequenceTopicName + ": 이벤트 발행");
    }

}
