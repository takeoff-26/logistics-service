package takeoff.logisticsservice.msa.delivery.deliveryRoute.application.kafka.dto;

import java.util.UUID;
import lombok.Builder;

/**
 * @author : hanjihoon
 * @Date : 2025. 04. 02.
 */
@Builder
public record KafkaDeliveryToDeliveryRouteDto(UUID deliveryId,
                                              UUID fromHubId,
                                              UUID toHubId) {

}
