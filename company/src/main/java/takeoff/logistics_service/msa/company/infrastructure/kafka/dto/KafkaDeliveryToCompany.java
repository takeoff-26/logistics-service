package takeoff.logistics_service.msa.company.infrastructure.kafka.dto;

import java.util.UUID;
import lombok.Builder;
import takeoff.logistics_service.msa.company.application.kafka.dto.KafkaDeliveryToCompanyDto;

/**
 * @author : hanjihoon
 * @Date : 2025. 04. 01.
 */
@Builder
public record KafkaDeliveryToCompany(UUID deliveryId,
                                     UUID companyId,
                                     UUID supplierId) {

    public KafkaDeliveryToCompanyDto toApplication() {
        return KafkaDeliveryToCompanyDto.builder()
            .deliveryId(deliveryId)
            .companyId(companyId)
            .supplierId(supplierId)
            .build();
    }

}
