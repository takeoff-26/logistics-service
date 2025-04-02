package takeoff.logisticsservice.msa.delivery.delivery.infrastructure.kafka.dto;

import java.util.UUID;
import lombok.Builder;
import takeoff.logisticsservice.msa.delivery.delivery.application.dto.kafka.KafkaDeliveryIdAndCompanyIdListenerDto;

/**
 * @author : hanjihoon
 * @Date : 2025. 04. 01.
 */
@Builder
public record KafkaDeliveryIdAndCompanyIdListener(UUID deliveryId,
                                                  Long companyManagerId) {

    public KafkaDeliveryIdAndCompanyIdListenerDto toApplication() {
        return KafkaDeliveryIdAndCompanyIdListenerDto.builder()
            .deliveryId(deliveryId)
            .companyManagerId(companyManagerId)
            .build();
    }
}
