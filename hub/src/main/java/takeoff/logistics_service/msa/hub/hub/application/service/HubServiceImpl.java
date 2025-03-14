package takeoff.logistics_service.msa.hub.hub.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import takeoff.logistics_service.msa.hub.hub.model.entity.Hub;
import takeoff.logistics_service.msa.hub.hub.model.repository.HubRepository;
import takeoff.logistics_service.msa.hub.hub.presentation.dto.request.PostHubRequestDto;
import takeoff.logistics_service.msa.hub.hub.presentation.dto.response.PostHubResponseDto;

/**
 * @author : hanjihoon
 * @Date : 2025. 03. 15.
 */
@Service
@RequiredArgsConstructor
@Transactional
public class HubServiceImpl implements HubService{

    private final HubRepository hubRepository;

    @Override
    public PostHubResponseDto saveHub(PostHubRequestDto requestDto) {
        return PostHubResponseDto.from(hubRepository.save(requestDto.toEntity()));
    }
}
