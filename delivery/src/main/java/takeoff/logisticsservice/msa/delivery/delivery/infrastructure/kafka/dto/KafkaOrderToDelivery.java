package takeoff.logisticsservice.msa.delivery.delivery.infrastructure.kafka.dto;

import java.util.UUID;
import lombok.Builder;
import takeoff.logisticsservice.msa.delivery.delivery.application.dto.kafka.kafkaOrderToDeliveryDto;

/**
 * @author : hanjihoon
 * @Date : 2025. 04. 01.
 */
@Builder
public record KafkaOrderToDelivery(UUID orderId,
                                   UUID companyId,
                                   Long customerId) {

    public kafkaOrderToDeliveryDto toApplication() {
        return kafkaOrderToDeliveryDto.builder()
            .orderId(orderId)
            .companyId(companyId)
            .customerId(customerId)
            .build();
    }

}
