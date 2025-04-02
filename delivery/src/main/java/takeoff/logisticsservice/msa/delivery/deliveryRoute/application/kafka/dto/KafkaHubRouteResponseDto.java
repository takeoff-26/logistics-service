package takeoff.logisticsservice.msa.delivery.deliveryRoute.application.kafka.dto;

import java.util.List;
import java.util.UUID;
import lombok.Builder;
import takeoff.logisticsservice.msa.delivery.deliveryRoute.application.client.dto.response.FindHubRoutes;

@Builder
public record KafkaHubRouteResponseDto(List<FindHubRoutes> hubAllListResponseList,
                                       UUID deliveryId) {

}
