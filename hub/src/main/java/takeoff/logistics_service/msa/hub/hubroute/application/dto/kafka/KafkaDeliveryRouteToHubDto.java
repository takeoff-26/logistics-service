package takeoff.logistics_service.msa.hub.hubroute.application.dto.kafka;

import java.util.UUID;
import lombok.Builder;

/**
 * @author : hanjihoon
 * @Date : 2025. 04. 02.
 */
@Builder
public record KafkaDeliveryRouteToHubDto(UUID deliveryId,
                                         UUID fromHubId,
                                         UUID toHubId) {

}
