package takeoff.logistics_service.msa.company.infrastructure.kafka.dto;

import java.util.UUID;
import lombok.Builder;
import takeoff.logistics_service.msa.company.application.kafka.dto.KafkaCompanyToOrderDto;

/**
 * @author : hanjihoon
 * @Date : 2025. 04. 01.
 */
@Builder
public record KafkaCompanyToOrder(UUID orderId,
                                  UUID toHubId) {

    public static KafkaCompanyToOrder from(KafkaCompanyToOrderDto kafkaCompanyToOrderDto) {
        return KafkaCompanyToOrder.builder()
            .orderId(kafkaCompanyToOrderDto.orderId())
            .toHubId(kafkaCompanyToOrderDto.toHubId())
            .build();
    }

}
