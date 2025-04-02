package takeoff.logisticsservice.msa.delivery.delivery.application.dto.kafka;

import java.util.UUID;
import lombok.Builder;

/**
 * @author : hanjihoon
 * @Date : 2025. 04. 02.
 */
@Builder
public record KafkaCompanyToDeliveryDto(UUID deliveryId,
                                        UUID fromHubId,
                                        UUID toHubId) {

}
