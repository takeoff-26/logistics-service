package takeoff.logisticsservice.msa.delivery.delivery.application.kafka;

import takeoff.logisticsservice.msa.delivery.delivery.application.dto.kafka.KafkaCompanyToDeliveryDto;
import takeoff.logisticsservice.msa.delivery.delivery.application.dto.kafka.KafkaDeliverySequenceDto;
import takeoff.logisticsservice.msa.delivery.delivery.application.dto.kafka.KafkaDeliveryToCompany;
import takeoff.logisticsservice.msa.delivery.delivery.application.dto.kafka.KafkaOrderUpdateDto;

/**
 * @author : hanjihoon
 * @Date : 2025. 04. 01.
 */
public interface DeliveryEventProducer {

    void sendToDeliverySequence(KafkaDeliverySequenceDto event);

    void sendToCompany(KafkaDeliveryToCompany kafkaDeliveryToCompany);

    void sendToOrder(KafkaOrderUpdateDto kafkaOrderUpdateDto);

    void sendToDeliveryRoute(KafkaCompanyToDeliveryDto kafkaCompanyToDeliveryDto);
}
