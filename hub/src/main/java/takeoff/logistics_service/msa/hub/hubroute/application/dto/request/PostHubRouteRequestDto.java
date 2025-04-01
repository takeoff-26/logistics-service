package takeoff.logistics_service.msa.hub.hubroute.application.dto.request;

import java.util.UUID;
import lombok.Builder;
import takeoff.logistics_service.msa.hub.hubroute.application.dto.kafka.KafkaDeliveryRouteToHubDto;

/**
 * @author : hanjihoon
 * @Date : 2025. 03. 15.
 */
@Builder
public record PostHubRouteRequestDto(UUID fromHubId,
                                     UUID toHubId) {

    public static PostHubRouteRequestDto createDto(UUID fromHubId, UUID toHubId) {
        return new PostHubRouteRequestDto(fromHubId, toHubId);
    }

    //kafka
    public static PostHubRouteRequestDto from(KafkaDeliveryRouteToHubDto kafkaDeliveryRouteToHubDto) {
        return PostHubRouteRequestDto.builder()
            .fromHubId(kafkaDeliveryRouteToHubDto.fromHubId())
            .toHubId(kafkaDeliveryRouteToHubDto.toHubId())
            .build();

    }

}
