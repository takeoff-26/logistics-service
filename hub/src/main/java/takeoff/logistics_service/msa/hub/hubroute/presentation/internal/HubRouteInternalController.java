package takeoff.logistics_service.msa.hub.hubroute.presentation.internal;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import takeoff.logistics_service.msa.hub.hubroute.application.service.HubRouteService;

/**
 * @author : hanjihoon
 * @Date : 2025. 03. 15.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/app/hubRoutes")
public class HubRouteInternalController {

    private final HubRouteService hubRouteService;

//    @PostMapping
//    public List<PostHubRouteResponseDto> createHubRoute(@RequestBody PostHubRouteRequestDto requestDto) {
//        return hubRouteService.createHubRoute(requestDto);
//    }

}
