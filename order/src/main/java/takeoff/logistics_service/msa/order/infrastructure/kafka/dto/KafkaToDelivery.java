package takeoff.logistics_service.msa.order.infrastructure.kafka.dto;

import java.util.UUID;
import lombok.Builder;

/**
 * @author : hanjihoon
 * @Date : 2025. 04. 01.
 */
@Builder
public record KafkaToDelivery(UUID orderId,
                              UUID companyId,
                              Long customerId) {

    public static KafkaToDelivery from(UUID orderId, UUID companyId, Long customerId) {
        return KafkaToDelivery.builder()
            .orderId(orderId)
            .companyId(companyId)
            .customerId(customerId)
            .build();
    }

}
