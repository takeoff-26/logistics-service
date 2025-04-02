package takeoff.logisticsservice.msa.delivery.deliveryRoute.infrastructure.kafka.dto;

import java.util.UUID;
import lombok.Builder;
import takeoff.logisticsservice.msa.delivery.deliveryRoute.application.kafka.dto.KafkaDeliveryToDeliveryRouteDto;

/**
 * @author : hanjihoon
 * @Date : 2025. 04. 02.
 */
@Builder
public record KafkaDeliveryToDeliveryRoute(UUID deliveryId,
                                           UUID fromHubId,
                                           UUID toHubId) {

    public KafkaDeliveryToDeliveryRouteDto toApplication() {
        return KafkaDeliveryToDeliveryRouteDto.builder()
            .deliveryId(deliveryId)
            .fromHubId(fromHubId)
            .toHubId(toHubId)
            .build();
    }

}
