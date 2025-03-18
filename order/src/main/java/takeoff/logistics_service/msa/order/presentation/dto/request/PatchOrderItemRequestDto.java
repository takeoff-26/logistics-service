package takeoff.logistics_service.msa.order.presentation.dto.request;

import java.util.UUID;

private record PatchOrderItemRequestDto(
    UUID productId,
    int quantity
) {

}
