package takeoff.logisticsservice.msa.delivery.delivery.application.dto.kafka;

import java.util.UUID;
import lombok.Builder;

/**
 * @author : hanjihoon
 * @Date : 2025. 04. 01.
 */
@Builder
public record KafkaDeliveryToCompany(UUID deliveryId,
                                     UUID companyId,
                                     UUID supplierId) {

    public static KafkaDeliveryToCompany from(UUID deliveryId, UUID companyId, UUID supplierId) {
        return KafkaDeliveryToCompany.builder()
            .deliveryId(deliveryId)
            .companyId(companyId)
            .supplierId(supplierId)
            .build();
    }

}
