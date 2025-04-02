package takeoff.logistics_service.msa.order.infrastructure.kafka.dto;

import java.util.UUID;
import lombok.Builder;
import takeoff.logistics_service.msa.order.application.service.kafka.dto.KafkaCompanyDto;

/**
 * @author : hanjihoon
 * @Date : 2025. 04. 01.
 */
@Builder
public record KafkaCompany(UUID orderId,
                           UUID toHubId,
                           UUID fromHubId) {

    public KafkaCompanyDto toApplication() {
        return KafkaCompanyDto.builder()
            .orderId(orderId)
            .toHubId(toHubId)
            .fromHubId(fromHubId)
            .build();
    }

}
