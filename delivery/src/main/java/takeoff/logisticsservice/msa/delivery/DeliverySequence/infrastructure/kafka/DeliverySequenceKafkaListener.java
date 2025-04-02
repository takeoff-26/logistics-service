package takeoff.logisticsservice.msa.delivery.DeliverySequence.infrastructure.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import takeoff.logisticsservice.msa.delivery.DeliverySequence.application.DeliverySequenceService;
import takeoff.logisticsservice.msa.delivery.DeliverySequence.application.dto.kafka.KafkaDeliverySequenceListenerDto;

/**
 * @author : hanjihoon
 * @Date : 2025. 03. 31.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class DeliverySequenceKafkaListener {

    private final DeliverySequenceService deliverySequenceService;

    @KafkaListener(
        topics = "delivery-sequence-events",
        containerFactory = "UUIDContainerFactory"
    )
    public void handleHubRouteListResponse(KafkaDeliverySequenceListenerDto event) {
        log.info("허브 라우트 리스트 응답 수신: {}", event);
        deliverySequenceService.findNextCompanyDeliverySequenceKafka(event);
    }


}
