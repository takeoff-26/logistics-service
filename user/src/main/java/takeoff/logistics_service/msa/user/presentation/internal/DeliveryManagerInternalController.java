package takeoff.logistics_service.msa.user.presentation.internal;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import takeoff.logistics_service.msa.user.application.service.DeliveryManagerService;
import takeoff.logistics_service.msa.user.presentation.dto.response.GetDeliveryManagerResponseDto;

@RestController
@RequestMapping("/api/v1/app/delivery-managers")
@RequiredArgsConstructor
public class DeliveryManagerInternalController {

    private final DeliveryManagerService deliveryManagerService;

    @GetMapping("/{id}")
    public GetDeliveryManagerResponseDto getDeliveryManager(@PathVariable Long id) {
        return deliveryManagerService.getDeliveryManagerById(id);
    }
}
