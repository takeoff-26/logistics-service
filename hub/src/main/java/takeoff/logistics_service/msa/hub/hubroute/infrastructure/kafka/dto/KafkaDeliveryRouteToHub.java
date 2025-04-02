package takeoff.logistics_service.msa.hub.hubroute.infrastructure.kafka.dto;

import java.util.UUID;
import lombok.Builder;
import takeoff.logistics_service.msa.hub.hubroute.application.dto.kafka.KafkaDeliveryRouteToHubDto;

/**
 * @author : hanjihoon
 * @Date : 2025. 04. 02.
 */
@Builder
public record KafkaDeliveryRouteToHub(UUID deliveryId,
                                      UUID fromHubId,
                                      UUID toHubId) {

    public KafkaDeliveryRouteToHubDto toApplication() {
        return KafkaDeliveryRouteToHubDto.builder()
            .deliveryId(deliveryId)
            .fromHubId(fromHubId)
            .toHubId(toHubId)
            .build();
    }
}
