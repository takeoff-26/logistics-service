package takeoff.logistics_service.msa.order.application.service.kafka.dto;

import java.util.UUID;
import lombok.Builder;

/**
 * @author : hanjihoon
 * @Date : 2025. 04. 01.
 */
@Builder
public record KafkaCompanyDto(UUID orderId,
                              UUID toHubId,
                              UUID fromHubId) {

    public static KafkaCompanyDto from(UUID orderId, UUID toHubId, UUID fromHubId){
        return KafkaCompanyDto.builder()
            .orderId(orderId)
            .toHubId(toHubId)
            .fromHubId(fromHubId)
            .build();
    }

}
