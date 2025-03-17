package takeoff.logistics_service.msa.order.presentation.dto.response;


import java.util.List;
import java.util.UUID;

public record OrderSaveResponseDto(
    UUID orderId,
    UUID supplierId,
    List<OrderItemSaveResponseDto> orderItems,
    Long customerId,
    String deliveryAddress,
    String requestNotes
) {

  private record OrderItemSaveResponseDto(
      UUID productId,
      int quantity
  ) {

  }
}
