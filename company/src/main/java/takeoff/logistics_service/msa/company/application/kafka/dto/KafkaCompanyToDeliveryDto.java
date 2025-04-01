package takeoff.logistics_service.msa.company.application.kafka.dto;

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

    public static KafkaCompanyToDeliveryDto from(UUID deliveryId, UUID fromHubId,
        UUID toHubId) {
        return KafkaCompanyToDeliveryDto.builder()
            .deliveryId(deliveryId)
            .fromHubId(fromHubId)
            .toHubId(toHubId)
            .build();
    }
}
