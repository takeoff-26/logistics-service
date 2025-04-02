package takeoff.logistics_service.msa.hub.hubroute.application.dto.kafka;

import java.util.List;
import java.util.UUID;
import lombok.Builder;
import takeoff.logistics_service.msa.hub.hubroute.application.dto.FindHubRoutesDto;

/**
 * @author : hanjihoon
 * @Date : 2025. 03. 18.
 */
@Builder
public record KafkaHubRoutesDto(List<FindHubRoutesDto> hubRoutesDtoList,
                                UUID deliveryId) {

}
