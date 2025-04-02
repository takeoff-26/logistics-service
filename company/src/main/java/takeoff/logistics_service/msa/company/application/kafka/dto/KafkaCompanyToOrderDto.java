package takeoff.logistics_service.msa.company.application.kafka.dto;

import java.util.UUID;
import lombok.Builder;

/**
 * @author : hanjihoon
 * @Date : 2025. 04. 01.
 */
@Builder
public record KafkaCompanyToOrderDto(UUID orderId,
                                     UUID toHubId,
                                     UUID fromHubId) {

}
