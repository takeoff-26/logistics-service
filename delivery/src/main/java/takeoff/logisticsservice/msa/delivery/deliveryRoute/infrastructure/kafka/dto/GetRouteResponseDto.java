package takeoff.logisticsservice.msa.delivery.deliveryRoute.infrastructure.kafka.dto;

import java.util.UUID;

/**
 * @author : hanjihoon
 * @Date : 2025. 04. 02.
 */
public record GetRouteResponseDto(UUID hubId,
                                  String hubName,
                                  String address,
                                  Double latitude,
                                  Double longitude) {

}
