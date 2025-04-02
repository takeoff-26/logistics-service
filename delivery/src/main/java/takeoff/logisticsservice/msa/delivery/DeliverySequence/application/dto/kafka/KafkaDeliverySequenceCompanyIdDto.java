package takeoff.logisticsservice.msa.delivery.DeliverySequence.application.dto.kafka;

import java.util.UUID;
import lombok.Builder;

/**
 * @author : hanjihoon
 * @Date : 2025. 04. 01.
 */
@Builder
public record KafkaDeliverySequenceCompanyIdDto(UUID deliveryId,
                                                Long companyManagerId) {

    public static KafkaDeliverySequenceCompanyIdDto from(UUID deliveryId, Long companyManagerId) {
        return KafkaDeliverySequenceCompanyIdDto.builder()
            .deliveryId(deliveryId)
            .companyManagerId(companyManagerId)
            .build();
    }

}
