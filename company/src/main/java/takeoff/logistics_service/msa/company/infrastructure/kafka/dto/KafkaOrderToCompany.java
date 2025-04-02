package takeoff.logistics_service.msa.company.infrastructure.kafka.dto;

import java.util.UUID;
import lombok.Builder;
import takeoff.logistics_service.msa.company.application.kafka.dto.KafkaOrderToCompanyDto;

/**
 * @author : hanjihoon
 * @Date : 2025. 04. 01.
 */
@Builder
public record KafkaOrderToCompany(UUID orderId,
                                  UUID companyId,
                                  UUID supplierId) {

    public KafkaOrderToCompanyDto toApplication() {
        return KafkaOrderToCompanyDto.builder()
            .orderId(orderId)
            .companyId(companyId)
            .supplierId(supplierId)
            .build();
    }
}
