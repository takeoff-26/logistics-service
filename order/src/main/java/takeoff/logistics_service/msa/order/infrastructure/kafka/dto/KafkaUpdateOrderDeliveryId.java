package takeoff.logistics_service.msa.order.infrastructure.kafka.dto;

import java.util.UUID;
import lombok.Builder;
import takeoff.logistics_service.msa.order.application.service.kafka.dto.KafkaUpdateOrderDeliveryIdDto;

/**
 * @author : hanjihoon
 * @Date : 2025. 04. 02.
 */
@Builder
public record KafkaUpdateOrderDeliveryId(UUID orderId,
                                         UUID deliveryId) {

    public KafkaUpdateOrderDeliveryIdDto toApplication() {
        return KafkaUpdateOrderDeliveryIdDto.builder()
            .orderId(orderId)
            .deliveryId(deliveryId)
            .build();
    }

}
