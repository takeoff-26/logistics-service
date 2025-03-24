package takeoff.logistics_service.msa.user.application.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import takeoff.logistics_service.msa.common.domain.UserInfoDto;
import takeoff.logistics_service.msa.user.domain.entity.DeliveryManager;

import takeoff.logistics_service.msa.user.domain.entity.UserRole;

import takeoff.logistics_service.msa.user.domain.repository.UserRepository;
import takeoff.logistics_service.msa.user.domain.service.DeliveryManagerSearchCondition;
import takeoff.logistics_service.msa.user.domain.service.SearchQueryService;
import takeoff.logistics_service.msa.user.domain.vo.DeliveryManagerType;
import takeoff.logistics_service.msa.user.domain.vo.DeliverySequence;
import takeoff.logistics_service.msa.user.domain.vo.HubId;
import takeoff.logistics_service.msa.user.presentation.common.dto.PaginationDto;
import takeoff.logistics_service.msa.user.presentation.dto.request.GetDeliveryManagerListRequestDto;
import takeoff.logistics_service.msa.user.presentation.dto.request.PatchDeliveryManagerRequestDto;
import takeoff.logistics_service.msa.user.presentation.dto.request.PostDeliveryManagerRequestDto;
import takeoff.logistics_service.msa.user.presentation.dto.response.*;
import static takeoff.logistics_service.msa.user.application.exception.UserErrorCode.*;
import takeoff.logistics_service.msa.user.application.exception.UserBusinessException;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class DeliveryManagerServiceImpl implements DeliveryManagerService {

    private final UserRepository userRepository;
    private final SearchQueryService searchQueryService;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public PostDeliveryManagerResponseDto createDeliveryManager(PostDeliveryManagerRequestDto requestDto, UserInfoDto userInfoDto) {
        boolean isDuplicate = userRepository.findByUsername(requestDto.username())
                .filter(user -> user.getRole().equals(requestDto.role()))
                .isPresent();

        if (isDuplicate) {
            throw UserBusinessException.from(USERNAME_ALREADY_EXISTS);
        }

//        int nextSequence = 0;
//        if (requestDto.role().equals(UserRole.COMPANY_DELIVERY_MANAGER)) {
//            HubId hubId = HubId.from(UUID.fromString(requestDto.identifier()));
//
//            if (requestDto.deliveryManagerType() == DeliveryManagerType.COMPANY_DELIVERY_MANAGER) {
//                nextSequence = userRepository.countCompanyDeliveryManagersByHubId(hubId);
//            } else if (requestDto.deliveryManagerType() == DeliveryManagerType.HUB_DELIVERY_MANAGER) {
//                nextSequence = userRepository.countHubDeliveryManagersByHubId(hubId);
//            } else {
//                throw new IllegalArgumentException("지원하지 않는 배송 관리자 타입입니다.");
//            }
//            if (nextSequence >= 10) {
//                throw new IllegalStateException("해당 허브에는 최대 10명의 배송 담당자만 등록할 수 있습니다.");
//            }
//        }

        String encodePassword = passwordEncoder.encode(requestDto.password());
        log.info(encodePassword);
        DeliveryManager deliveryManager = requestDto.toEntityWithSequence(encodePassword);

        userRepository.save(deliveryManager);
        return PostDeliveryManagerResponseDto.from(deliveryManager);
    }

    @Override
    @Transactional
    public PatchDeliveryManagerResponseDto updateDeliveryManager(Long id, PatchDeliveryManagerRequestDto requestDto, UserInfoDto userInfoDto) {
        DeliveryManager manager = userRepository.findDeliveryManagerById(id)
                .orElseThrow(() -> UserBusinessException.from(DELIVERY_MANAGER_NOT_FOUND));

        if (requestDto.hubId() != null || requestDto.companyId() != null) {
            String newIdentifier = requestDto.hubId() != null ? requestDto.hubId() : requestDto.companyId();
            manager.updateIdentifier(newIdentifier);
        }

        manager.updateDeliveryManager(
                requestDto.slackEmail() != null ? requestDto.slackEmail() : manager.getSlackEmail(),
                requestDto.deliverySequence() != null ? DeliverySequence.from(requestDto.deliverySequence()) : manager.getDeliverySequence()
        );

        return PatchDeliveryManagerResponseDto.from(manager);
    }

    @Override
    public GetDeliveryManagerResponseDto getDeliveryManagerById(Long id, UserInfoDto userInfoDto) {
        DeliveryManager manager = userRepository.findDeliveryManagerById(id)
                .orElseThrow(() -> UserBusinessException.from(DELIVERY_MANAGER_NOT_FOUND));
        return GetDeliveryManagerResponseDto.from(manager);
    }

    @Override
    public GetDeliveryManagerListResponseDto getAllDeliveryManagers(GetDeliveryManagerListRequestDto requestDto, UserInfoDto userInfoDto) {
        DeliveryManagerSearchCondition condition = requestDto.toCondition();
        Pageable pageable = requestDto.toPageable();
        Page<DeliveryManager> deliveryManagers = searchQueryService.searchDeliveryManagers(condition, pageable);

        List<GetDeliveryManagerListInfoDto> deliveryManagerList = deliveryManagers.getContent().stream()
                .map(GetDeliveryManagerListInfoDto::from)
                .toList();

        return new GetDeliveryManagerListResponseDto(deliveryManagerList, PaginationDto.from(deliveryManagers));
    }

    @Override
    @Transactional
    public DeleteDeliveryManagerResponseDto deleteDeliveryManager(Long id, UserInfoDto deletedBy) {
        DeliveryManager manager = userRepository.findDeliveryManagerById(id)
                .orElseThrow(() -> UserBusinessException.from(DELIVERY_MANAGER_NOT_FOUND));

        if (manager.isDeleted()) {
            throw UserBusinessException.from(ALREADY_DELETED);
        }
        manager.deleteDeliveryManager(deletedBy.userId());
        return DeleteDeliveryManagerResponseDto.from(manager.getId());
    }

    @Override
    public List<GetDeliveryManagerListInfoDto> getCompanyDeliveryManagersByHubId(UUID hubId, UserInfoDto userInfoDto) {
        return userRepository.findAllCompanyDeliveryManagersByHubId(HubId.from(hubId)).stream()
                .map(GetDeliveryManagerListInfoDto::from)
                .toList();
    }

    @Override
    public List<GetDeliveryManagerListInfoDto> getAllHubDeliveryManagers(UserInfoDto userInfoDto) {
        return userRepository.findAllHubDeliveryManagers().stream()
                .map(GetDeliveryManagerListInfoDto::from)
                .toList();
    }
}
