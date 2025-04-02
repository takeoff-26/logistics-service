package takeoff.logisticsservice.msa.delivery.deliveryRoute.application.client.dto.response;

import java.util.List;
import lombok.Builder;

@Builder
public record PostHubRouteResponseDto(List<FindHubRoutes> hubAllListResponseList) {

}
