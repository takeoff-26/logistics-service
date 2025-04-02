package takeoff.logisticsservice.msa.delivery.delivery.application.dto.kafka;

import java.util.UUID;
import lombok.Builder;

/**
 * @author : hanjihoon
 * @Date : 2025. 04. 01.
 */
@Builder
public record KafkaDeliveryIdAndCompanyIdListenerDto(UUID deliveryId,
                                                     Long companyManagerId) {

}
