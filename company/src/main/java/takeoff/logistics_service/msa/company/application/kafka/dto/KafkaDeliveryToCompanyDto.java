package takeoff.logistics_service.msa.company.application.kafka.dto;

import java.util.UUID;
import lombok.Builder;

/**
 * @author : hanjihoon
 * @Date : 2025. 04. 01.
 */
@Builder
public record KafkaDeliveryToCompanyDto(UUID deliveryId,
                                        UUID companyId,
                                        UUID supplierId) {

    public static KafkaDeliveryToCompanyDto from(UUID deliveryId,
        UUID companyHubId, UUID supplierHubId) {
        return KafkaDeliveryToCompanyDto.builder()
            .deliveryId(deliveryId)
            .companyId(companyHubId)
            .supplierId(supplierHubId)
            .build();
    }

}
