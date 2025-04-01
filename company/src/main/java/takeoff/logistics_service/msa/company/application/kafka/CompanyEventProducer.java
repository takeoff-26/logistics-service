package takeoff.logistics_service.msa.company.application.kafka;

import takeoff.logistics_service.msa.company.application.kafka.dto.KafkaCompanyToDeliveryDto;
import takeoff.logistics_service.msa.company.application.kafka.dto.KafkaDeliveryToCompanyDto;

/**
 * @author : hanjihoon
 * @Date : 2025. 04. 01.
 */
public interface CompanyEventProducer {

    void sendToOrder(KafkaCompanyToDeliveryDto kafkaCompanyToDeliveryDto);

    void sendToDelivery(KafkaCompanyToDeliveryDto kafkaCompanyToDeliveryDto);
}
