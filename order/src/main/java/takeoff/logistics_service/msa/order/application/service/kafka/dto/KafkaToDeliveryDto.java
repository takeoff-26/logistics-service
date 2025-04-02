package takeoff.logistics_service.msa.order.application.service.kafka.dto;

import java.util.UUID;
import lombok.Builder;

/**
 * @author : hanjihoon
 * @Date : 2025. 04. 01.
 */
@Builder
public record KafkaToDeliveryDto(UUID orderId,
                                 UUID companyId,
                                 Long customerId) {

    public static KafkaToDeliveryDto from(UUID orderId, UUID companyId, Long customerId) {
        return KafkaToDeliveryDto.builder()
            .orderId(orderId)
            .companyId(companyId)
            .customerId(customerId)
            .build();
    }

}
