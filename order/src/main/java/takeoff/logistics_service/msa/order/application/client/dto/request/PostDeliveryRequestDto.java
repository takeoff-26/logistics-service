package takeoff.logistics_service.msa.order.application.client.dto.request;

import java.util.UUID;
import lombok.Builder;

@Builder
public record PostDeliveryRequestDto(UUID orderID, Long customerId, UUID fromHubId, UUID toHubId) {

    public static PostDeliveryRequestDto from(UUID orderID, Long customerId, UUID fromHubId, UUID toHubId) {
        return PostDeliveryRequestDto.builder()
            .orderID(orderID)
            .customerId(customerId)
            .fromHubId(fromHubId)
            .toHubId(toHubId)
            .build();
    }
}
