package takeoff.logistics_service.msa.hub.hubroute.application.service;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import takeoff.logistics_service.msa.hub.hubroute.application.dto.request.PutHubRouteRequestDto;
import takeoff.logistics_service.msa.hub.hubroute.application.dto.response.GetHubRouteResponseDto;
import takeoff.logistics_service.msa.hub.hubroute.application.dto.response.PutHubRouteResponseDto;
import takeoff.logistics_service.msa.hub.hubroute.domain.entity.HubRoute;
import takeoff.logistics_service.msa.hub.hubroute.domain.repository.HubRouteRepository;

/**
 * @author : hanjihoon
 * @Date : 2025. 03. 15.
 */
@Service
@RequiredArgsConstructor
@Transactional
public class HubRouteServiceImpl implements HubRouteService{

    private final HubRouteRepository hubRouteRepository;

    @Override
    @Transactional(readOnly = true)
    public GetHubRouteResponseDto findByHubRoute(UUID hubRouteId) {
        HubRoute hubRoute = getHubRoute(
            hubRouteId);
        return GetHubRouteResponseDto.from(hubRoute);
    }

    @Override
    public PutHubRouteResponseDto updateHubRoute(UUID hubRouteId,
        PutHubRouteRequestDto requestDto) {
        HubRoute hubRoute = getHubRoute(
            hubRouteId);
        hubRoute.modifyHubRoute(
            requestDto.fromHubId(),
            requestDto.toHubId(),
            requestDto.distance().getDistanceKm(),
            requestDto.duration().getDurationMinutes());
        return PutHubRouteResponseDto.from(hubRoute);
    }

    //baseEntity 생성후 수정
    @Override
    public void deleteHubRoute(UUID hubRouteId) {
        getHubRoute(hubRouteId);

    }

    //허브 이동경로 생성은 알고리즘 때문에 고민 좀 더 해보고 작성하겠습니다..
//    @Override
//    public List<PostHubRouteResponseDto> createHubRoute(PostHubRouteRequestDto requestDto) {
//
//    }

    private HubRoute getHubRoute(UUID hubRouteId) {
        return hubRouteRepository.findById(hubRouteId)
            .orElseThrow(() -> new IllegalArgumentException("찾을 수 없는 허브 이동경로 입니다."));
    }


}
