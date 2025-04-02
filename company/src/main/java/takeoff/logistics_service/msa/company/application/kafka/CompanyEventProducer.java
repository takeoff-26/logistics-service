package takeoff.logistics_service.msa.company.application.kafka;

import takeoff.logistics_service.msa.company.application.kafka.dto.KafkaCompanyToDeliveryDto;
import takeoff.logistics_service.msa.company.application.kafka.dto.KafkaCompanyToOrderDto;

/**
 * @author : hanjihoon
 * @Date : 2025. 04. 01.
 */
public interface CompanyEventProducer {

    void sendToOrder(KafkaCompanyToOrderDto kafkaCompanyToOrderDto);

    void sendToDelivery(KafkaCompanyToDeliveryDto kafkaCompanyToDeliveryDto);
}
