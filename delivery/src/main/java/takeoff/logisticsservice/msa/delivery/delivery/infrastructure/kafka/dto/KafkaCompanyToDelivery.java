package takeoff.logisticsservice.msa.delivery.delivery.infrastructure.kafka.dto;

import java.util.UUID;
import lombok.Builder;
import takeoff.logisticsservice.msa.delivery.delivery.application.dto.kafka.KafkaCompanyToDeliveryDto;

/**
 * @author : hanjihoon
 * @Date : 2025. 04. 02.
 */
@Builder
public record KafkaCompanyToDelivery(UUID deliveryId,
                                     UUID fromHubId,
                                     UUID toHubId) {

    public KafkaCompanyToDeliveryDto toApplication() {
        return KafkaCompanyToDeliveryDto.builder()
            .deliveryId(deliveryId)
            .fromHubId(fromHubId)
            .toHubId(toHubId)
            .build();
    }

}
