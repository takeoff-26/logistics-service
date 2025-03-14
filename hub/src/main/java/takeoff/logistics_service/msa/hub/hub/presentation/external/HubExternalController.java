package takeoff.logistics_service.msa.hub.hub.presentation.external;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import takeoff.logistics_service.msa.hub.hub.application.service.HubService;
import takeoff.logistics_service.msa.hub.hub.presentation.dto.request.PostHubRequestDto;
import takeoff.logistics_service.msa.hub.hub.presentation.dto.response.PostHubResponseDto;

/**
 * @author : hanjihoon
 * @Date : 2025. 03. 15.
 */
@RestController
@RequiredArgsConstructor
public class HubExternalController {

    private HubService hubService;

    @PostMapping
    public ResponseEntity<PostHubResponseDto> saveHub(@RequestBody PostHubRequestDto requestDto) {
        return ResponseEntity.ok(hubService.saveHub(requestDto));
    }

}
