package takeoff.logisticsservice.msa.delivery.delivery.application;

import static takeoff.logistics_service.msa.common.domain.UserRole.COMPANY_DELIVERY_MANAGER;
import static takeoff.logistics_service.msa.common.domain.UserRole.HUB_MANAGER;

import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import takeoff.logistics_service.msa.common.domain.UserRole;
import takeoff.logistics_service.msa.common.exception.BusinessException;
import takeoff.logistics_service.msa.common.exception.code.CommonErrorCode;
import takeoff.logisticsservice.msa.delivery.delivery.application.client.DeliverySequenceClientInternalDelivery;
import takeoff.logisticsservice.msa.delivery.delivery.application.client.UserClient;
import takeoff.logisticsservice.msa.delivery.delivery.application.dto.PaginatedResultDto;
import takeoff.logisticsservice.msa.delivery.delivery.application.dto.kafka.KafkaCompanyToDeliveryDto;
import takeoff.logisticsservice.msa.delivery.delivery.application.dto.kafka.KafkaDeliveryIdAndCompanyIdListenerDto;
import takeoff.logisticsservice.msa.delivery.delivery.application.dto.kafka.KafkaDeliverySequenceDto;
import takeoff.logisticsservice.msa.delivery.delivery.application.dto.kafka.KafkaDeliveryToCompany;
import takeoff.logisticsservice.msa.delivery.delivery.application.dto.kafka.KafkaOrderUpdateDto;
import takeoff.logisticsservice.msa.delivery.delivery.application.dto.kafka.kafkaOrderToDeliveryDto;
import takeoff.logisticsservice.msa.delivery.delivery.application.dto.request.SearchDeliveryRequestDto;
import takeoff.logisticsservice.msa.delivery.delivery.application.dto.response.SearchDeliveryResponseDto;
import takeoff.logisticsservice.msa.delivery.delivery.application.exception.DeliveryBusinessException;
import takeoff.logisticsservice.msa.delivery.delivery.application.exception.DeliveryErrorCode;
import takeoff.logisticsservice.msa.delivery.delivery.application.kafka.DeliveryEventProducer;
import takeoff.logisticsservice.msa.delivery.delivery.domain.entity.Delivery;
import takeoff.logisticsservice.msa.delivery.delivery.domain.entity.DeliveryId;
import takeoff.logisticsservice.msa.delivery.delivery.domain.entity.DeliveryStatus;
import takeoff.logisticsservice.msa.delivery.delivery.domain.repository.DeliveryRepository;
import takeoff.logisticsservice.msa.delivery.delivery.presentation.dto.request.PatchDeliveryRequestDto;
import takeoff.logisticsservice.msa.delivery.delivery.presentation.dto.request.PostDeliveryRequestDto;


@Service
@RequiredArgsConstructor
public class DeliveryService {

  private final DeliveryRepository deliveryRepository;
  private final DeliverySequenceClientInternalDelivery deliverySequenceClient;
  private final UserClient userClient;
  private final DeliveryEventProducer deliveryEventProducer;


  @Transactional
  public UUID saveDelivery(PostDeliveryRequestDto dto) {

    Long deliveryManagerId = deliverySequenceClient.findNextCompanyDeliverySequence(
        dto.toHubId()
    ).companyDeliveryManagerId();

    //kafka 설정에서 빌더 사용을 위한 직접 생성
    Delivery delivery = new Delivery(
        UUID.randomUUID(), dto.orderID(), deliveryManagerId,
        dto.customerId(), dto.fromHubId(), dto.toHubId());

    deliveryRepository.save(delivery);

    return delivery.getId().getId();
  }

  @Transactional
  public void updateDeliveryStatus(PatchDeliveryRequestDto dto, Long userId, UserRole userRole) {
    Delivery delivery = deliveryRepository.findById(DeliveryId.from(dto.deliveryId()))
        .orElseThrow(() -> DeliveryBusinessException.from(DeliveryErrorCode.DELIVERY_NOT_FOUND));

    if (userRole.equals(HUB_MANAGER)) {
      validateHubManagerAccess(delivery.getToHubId(), userId);
    }

    if (userRole.equals(COMPANY_DELIVERY_MANAGER)) {
      validateDeliveryManagerAccess(delivery.getDeliveryManagerId(), userId);
    }

    try {
      delivery.modifyStatus(dto.status());
    } catch (IllegalArgumentException e) {
      throw DeliveryBusinessException.from(DeliveryErrorCode.INVALID_DELIVERY_STATUS);
    }
  }

  @Transactional
  public void deleteDelivery(UUID deliveryId, Long userId, UserRole userRole) {
    Delivery delivery = deliveryRepository.findById(DeliveryId.from(deliveryId))
        .orElseThrow(() -> DeliveryBusinessException.from(DeliveryErrorCode.DELIVERY_NOT_FOUND));

    if (userRole.equals(HUB_MANAGER)) {
      validateHubManagerAccess(delivery.getToHubId(), userId);
    }

    delivery.delete(userId);
  }

  @Transactional(readOnly = true)
  public PaginatedResultDto<SearchDeliveryResponseDto> searchDelivery(
      SearchDeliveryRequestDto dto,
      Long userId,
      UserRole userRole
  ) {
    return switch (userRole) {
      case MASTER_ADMIN -> PaginatedResultDto.from(deliveryRepository.findAllBySearchParams(
          dto.toSearchCriteria()
      ));
      case HUB_MANAGER -> PaginatedResultDto.from(deliveryRepository.findAllBySearchParams(
          dto.toSearchCriteria(getHubId(userId), null, null)
      ));
      case COMPANY_MANAGER -> PaginatedResultDto.from(deliveryRepository.findAllBySearchParams(
          dto.toSearchCriteria(null, userId, null)
      ));
      case HUB_DELIVERY_MANAGER, COMPANY_DELIVERY_MANAGER ->
          PaginatedResultDto.from(deliveryRepository.findAllBySearchParams(
              dto.toSearchCriteria(null, null, userId)
          ));
    };
  }

  @Transactional
  public List<UUID> findAllDeliveryIdByUser(Long userId) {
    List<Delivery> deliveries = deliveryRepository.findAllByDeliveryManagerId(userId);

    return deliveries.stream()
        .map(Delivery::getIdLiteral)
        .toList();
  }

  //kafka
  @Transactional
  public void saveDeliveryKafka(kafkaOrderToDeliveryDto kafkaOrderToDeliveryDto) {

    Delivery delivery = Delivery.builder()
        .id(UUID.randomUUID())
        .orderId(kafkaOrderToDeliveryDto.orderId())
        .customerId(kafkaOrderToDeliveryDto.customerId())
        .status(DeliveryStatus.PENDING)
        .build();

    Delivery savedDelivery = deliveryRepository.save(delivery);

    //companyDeliveryManagerId를 delivery에 설정해야한다.
    //DeliverySequence -> companyDeliveryManagerId를 담아 다시 딜리버리 에게 발행
    //companyDeliveryManagerId를 delivery에 set
    //tohub와 fromhub를 저장해야한다. 컴퍼니에 접근해 가져오기.
    deliveryEventProducer.sendToCompany(KafkaDeliveryToCompany.from(
        delivery.getIdLiteral(), kafkaOrderToDeliveryDto.companyId(),
        kafkaOrderToDeliveryDto.supplierId()
    ));

    //오더에 딜리버리 아이디 저장
    deliveryEventProducer.sendToOrder(
        KafkaOrderUpdateDto.from(
            kafkaOrderToDeliveryDto.orderId(), delivery.getId().getId()));
  }

  //kafka
  @Transactional
  public void updateDeliveryToDeliveryCompanyManager(KafkaDeliveryIdAndCompanyIdListenerDto event) {
    Delivery delivery = deliveryRepository.findById(DeliveryId.from(event.deliveryId()))
        .orElseThrow(() ->
            DeliveryBusinessException.from(DeliveryErrorCode.DELIVERY_NOT_FOUND));

    delivery.modifyDeliveryCompanyManager(event.companyManagerId());

    deliveryEventProducer.sendToDeliverySequence(
        KafkaDeliverySequenceDto.from(delivery.getIdLiteral(), delivery.getToHubId()));
  }

  //kafka company -> delivery
  @Transactional
  public void updateDeliveryToHubIdAndFromHubId(KafkaCompanyToDeliveryDto kafkaCompanyToDeliveryDto) {
    Delivery delivery = deliveryRepository.findById(
            DeliveryId.from(kafkaCompanyToDeliveryDto.deliveryId()))
        .orElseThrow(() ->
            DeliveryBusinessException.from(DeliveryErrorCode.DELIVERY_NOT_FOUND));

    delivery.modifyDeliveryToHubAndFromHub(
        kafkaCompanyToDeliveryDto.toHubId(),
        kafkaCompanyToDeliveryDto.fromHubId()
    );
    //여기서 딜리버리 라우트로 쏴야한다.
    deliveryEventProducer.sendToDeliveryRoute(kafkaCompanyToDeliveryDto);

  }



  private void validateHubManagerAccess(UUID resourceId, Long userId) {
    if (!getHubId(userId).equals(resourceId)) {
      throw BusinessException.from(CommonErrorCode.FORBIDDEN);
    }
  }

  private void validateDeliveryManagerAccess(Long resourceId, Long userId) {
    if (!resourceId.equals(userId)) {
      throw BusinessException.from(CommonErrorCode.FORBIDDEN);
    }

  }

  private UUID getHubId(Long userId) {
    return userClient.findByUserId(userId).hubId();
  }

  private UUID getCompanyId(Long userId) {
    return userClient.findByUserId(userId).companyId();
  }
}
