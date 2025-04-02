package takeoff.logisticsservice.msa.delivery.DeliverySequence.application.dto.kafka;

import java.util.UUID;

/**
 * @author : hanjihoon
 * @Date : 2025. 04. 01.
 */
public record KafkaDeliverySequenceListenerDto(UUID deliveryId,
                                               UUID toHubId) {

}
