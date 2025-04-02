package takeoff.logisticsservice.msa.delivery.delivery.application.dto.kafka;

import java.util.UUID;
import lombok.Builder;

/**
 * @author : hanjihoon
 * @Date : 2025. 04. 01.
 */
@Builder
public record KafkaDeliverySequenceDto(UUID deliveryId,
                                       UUID toHubId) {
    public static KafkaDeliverySequenceDto from(UUID deliveryId, UUID toHubId) {
        return KafkaDeliverySequenceDto.builder()
            .deliveryId(deliveryId)
            .toHubId(toHubId)
            .build();
    }
}
