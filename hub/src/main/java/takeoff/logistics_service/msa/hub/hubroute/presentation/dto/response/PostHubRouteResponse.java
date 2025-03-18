package takeoff.logistics_service.msa.hub.hubroute.presentation.dto.response;

import java.util.UUID;
import takeoff.logistics_service.msa.hub.hubroute.domain.entity.Distance;
import takeoff.logistics_service.msa.hub.hubroute.domain.entity.Duration;

/**
 * @author : hanjihoon
 * @Date : 2025. 03. 15.
 */
public record PostHubRouteResponse(UUID hubRouteId,
                                   Distance distance,
                                   Duration duration) {

}
