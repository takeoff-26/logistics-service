package takeoff.logisticsservice.msa.delivery.delivery.application.dto.kafka;

import java.util.UUID;
import lombok.Builder;

/**
 * @author : hanjihoon
 * @Date : 2025. 04. 02.
 */
@Builder
public record KafkaOrderUpdateDto(UUID orderId,
                                  UUID deliveryId) {

    public static KafkaOrderUpdateDto from(UUID orderId, UUID deliveryId) {
        return KafkaOrderUpdateDto.builder()
            .orderId(orderId)
            .deliveryId(deliveryId)
            .build();
    }


}
