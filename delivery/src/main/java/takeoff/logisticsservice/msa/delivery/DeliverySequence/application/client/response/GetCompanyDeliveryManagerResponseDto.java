package takeoff.logisticsservice.msa.delivery.DeliverySequence.application.client.response;

public record GetCompanyDeliveryManagerResponseDto(
    Long userId,
    Integer deliverySequence
) {
}
