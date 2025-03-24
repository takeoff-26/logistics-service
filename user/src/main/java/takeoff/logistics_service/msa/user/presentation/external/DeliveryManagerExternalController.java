package takeoff.logistics_service.msa.user.presentation.external;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import takeoff.logistics_service.msa.user.application.service.DeliveryManagerService;
import takeoff.logistics_service.msa.user.presentation.dto.request.GetDeliveryManagerListRequestDto;
import takeoff.logistics_service.msa.user.presentation.dto.request.PatchDeliveryManagerRequestDto;
import takeoff.logistics_service.msa.user.presentation.dto.request.PostDeliveryManagerRequestDto;
import takeoff.logistics_service.msa.user.presentation.dto.response.*;

@RestController
@RequestMapping("/api/v1/users/delivery-managers")
@RequiredArgsConstructor
public class DeliveryManagerExternalController {

    private final DeliveryManagerService deliveryManagerService;

    @PostMapping("/create")
    public ResponseEntity<PostDeliveryManagerResponseDto> createDeliveryManager(
            @Valid @RequestBody PostDeliveryManagerRequestDto requestDto) {
        return ResponseEntity.ok(deliveryManagerService.createDeliveryManager(requestDto));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<PatchDeliveryManagerResponseDto> updateDeliveryManager(
            @PathVariable Long id,
            @Valid @RequestBody PatchDeliveryManagerRequestDto requestDto) {
        return ResponseEntity.ok(deliveryManagerService.updateDeliveryManager(id, requestDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<DeleteDeliveryManagerResponseDto> deleteDeliveryManager(
            @PathVariable Long id){
        return ResponseEntity.ok(deliveryManagerService.deleteDeliveryManager(id));
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetDeliveryManagerResponseDto> getDeliveryManager(
            @PathVariable Long id) {
        return ResponseEntity.ok(deliveryManagerService.getDeliveryManagerById(id));
    }

    @GetMapping
    public ResponseEntity<GetDeliveryManagerListResponseDto> getDeliveryManagers(
            @ModelAttribute GetDeliveryManagerListRequestDto requestDto) {
        return ResponseEntity.ok(deliveryManagerService.getAllDeliveryManagers(requestDto));
    }


}
