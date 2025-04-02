package takeoff.logistics_service.msa.order.application.service.kafka.dto;

import java.util.UUID;
import lombok.Builder;

/**
 * @author : hanjihoon
 * @Date : 2025. 04. 02.
 */
@Builder
public record KafkaUpdateOrderDeliveryIdDto(UUID orderId,
                                            UUID deliveryId) {

}
