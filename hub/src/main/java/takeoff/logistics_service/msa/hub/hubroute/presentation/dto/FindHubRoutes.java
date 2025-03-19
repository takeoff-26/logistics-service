package takeoff.logistics_service.msa.hub.hubroute.presentation.dto;

import java.util.UUID;
import takeoff.logistics_service.msa.hub.hubroute.application.dto.DistanceDto;
import takeoff.logistics_service.msa.hub.hubroute.application.dto.DurationDto;

/**
 * @author : hanjihoon
 * @Date : 2025. 03. 19.
 */
public record FindHubRoutes(UUID hubRouteId,
                            UUID fromHubId,
                            UUID toHubId,
                            DistanceDto distanceDto,
                            DurationDto durationDto) {

}
