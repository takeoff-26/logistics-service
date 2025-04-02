package takeoff.logisticsservice.msa.delivery.deliveryRoute.application.kafka.dto;

import java.util.UUID;
import lombok.Builder;

@Builder
public record KafkaHubRouteRequestDto(
    UUID deliveryId,
    UUID fromHubId,
    UUID toHubId
) {

    public static KafkaHubRouteRequestDto from(UUID deliveryId, UUID fromHubId, UUID toHubId) {
        return KafkaHubRouteRequestDto.builder()
            .deliveryId(deliveryId)
            .fromHubId(fromHubId)
            .toHubId(toHubId)
            .build();
    }

}
