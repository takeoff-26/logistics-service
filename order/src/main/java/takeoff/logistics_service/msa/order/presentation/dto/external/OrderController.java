package takeoff.logistics_service.msa.order.presentation.dto.external;


import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import takeoff.logistics_service.msa.order.application.service.OrderService;
import takeoff.logistics_service.msa.order.presentation.dto.request.PatchOrderRequestDto;
import takeoff.logistics_service.msa.order.presentation.dto.request.PostOrderRequestDto;
import takeoff.logistics_service.msa.order.presentation.dto.response.PatchOrderResponseDto;
import takeoff.logistics_service.msa.order.presentation.dto.response.PostOrderResponseDto;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {

  private final OrderService orderService;

  @PostMapping
  public ResponseEntity<PostOrderResponseDto> saveOrder(@RequestBody PostOrderRequestDto dto) {
    return ResponseEntity.ok()
        .body(orderService.saveOrder(dto));
  }


  @PatchMapping("/{orderId}")
  public ResponseEntity<PatchOrderResponseDto> updateOrder(
      @RequestBody PatchOrderRequestDto dto,
      @PathVariable("orderId") UUID orderId) {
    return ResponseEntity.ok()
        .body(orderService.updateOrder(dto, orderId));
  }

  @DeleteMapping("/{orderId}")
  public ResponseEntity<Void> deleteOrder(@PathVariable("orderId") UUID orderId) {
    orderService.deleteOrder(orderId);
    return ResponseEntity.ok().build();
  }

}
