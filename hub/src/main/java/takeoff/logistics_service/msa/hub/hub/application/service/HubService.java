package takeoff.logistics_service.msa.hub.hub.application.service;

import takeoff.logistics_service.msa.hub.hub.presentation.dto.request.PostHubRequestDto;
import takeoff.logistics_service.msa.hub.hub.presentation.dto.response.PostHubResponseDto;

/**
 * @author : hanjihoon
 * @Date : 2025. 03. 15.
 */
public interface HubService {

    PostHubResponseDto saveHub(PostHubRequestDto requestDto);
}
