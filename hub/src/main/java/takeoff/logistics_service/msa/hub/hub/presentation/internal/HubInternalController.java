package takeoff.logistics_service.msa.hub.hub.presentation.internal;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import takeoff.logistics_service.msa.hub.hub.application.service.HubService;

/**
 * @author : hanjihoon
 * @Date : 2025. 03. 15.
 */
@RestController
@RequiredArgsConstructor
public class HubInternalController {

    private HubService hubService;

}
