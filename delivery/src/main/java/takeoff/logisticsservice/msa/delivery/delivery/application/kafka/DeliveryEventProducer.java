package takeoff.logisticsservice.msa.delivery.delivery.application.kafka;

import java.util.UUID;
import takeoff.logisticsservice.msa.delivery.delivery.application.dto.kafka.KafkaDeliverySequenceDto;

/**
 * @author : hanjihoon
 * @Date : 2025. 04. 01.
 */
public interface DeliveryEventProducer {

    void sendToDeliverySequence(KafkaDeliverySequenceDto event);

}
