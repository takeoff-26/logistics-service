package takeoff.logisticsservice.msa.delivery.DeliverySequence.application.kafka;

import takeoff.logisticsservice.msa.delivery.DeliverySequence.application.dto.kafka.KafkaDeliverySequenceCompanyIdDto;

/**
 * @author : hanjihoon
 * @Date : 2025. 04. 01.
 */
public interface DeliverySequenceEventProducer {

    void sendToDelivery(KafkaDeliverySequenceCompanyIdDto event);

}
