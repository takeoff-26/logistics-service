package takeoff.logistics_service.msa.order.application.service.kafka;

import takeoff.logistics_service.msa.order.application.service.kafka.dto.KafkaCompanyDto;
import takeoff.logistics_service.msa.order.application.service.kafka.dto.KafkaToDeliveryDto;
import takeoff.logistics_service.msa.order.infrastructure.kafka.dto.KafkaToDelivery;

/**
 * @author : hanjihoon
 * @Date : 2025. 03. 31.
 */
public interface OrderEventProducer {

    void sendToDelivery(KafkaToDeliveryDto kafkaToDeliveryDto);

    void sendToCompany(KafkaCompanyDto dto);
}
