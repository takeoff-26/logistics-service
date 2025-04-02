package takeoff.logisticsservice.msa.delivery.deliveryRoute.application.client.dto.request;

import java.util.UUID;
import lombok.Builder;

@Builder
public record PostHubRouteRequestDto(
    UUID fromHubId,
    UUID toHubId
) {

    public static PostHubRouteRequestDto from(UUID fromHubId, UUID toHubId) {
        return PostHubRouteRequestDto.builder()
            .fromHubId(fromHubId)
            .toHubId(toHubId)
            .build();
    }

}
