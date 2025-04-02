package takeoff.logistics_service.msa.hub.hubroute.infrastructure.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import takeoff.logistics_service.msa.hub.hubroute.application.dto.kafka.KafkaFromToHubListDto;
import takeoff.logistics_service.msa.hub.hubroute.application.service.HubRouteService;
import takeoff.logistics_service.msa.hub.hubroute.infrastructure.kafka.dto.KafkaDeliveryRouteToHub;

/**
 * @author : hanjihoon
 * @Date : 2025. 03. 31.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class HubRouteKafkaListener {

    private final HubRouteService hubRouteService;

    @KafkaListener(
        topics = "hub-events",
        containerFactory = "kafkaFromToHubListDtoContainerFactory"
    )
    public void handleHubRouteListResponse(KafkaFromToHubListDto event) {
        log.info("허브 라우트 리스트 응답 수신: {}", event);
        hubRouteService.createHubRouteExecute(event);
    }


    @KafkaListener(
        topics = "delivery-route-to-hub-events",
        containerFactory = "kafkaFromToHubListDtoContainerFactory"
    )
    public void handleDeliveryRouteToHubResponse(KafkaDeliveryRouteToHub kafkaDeliveryRouteToHub) {
        log.info("허브 라우트 리스트 응답 수신: {}", kafkaDeliveryRouteToHub);
        hubRouteService.deliveryRouteToHubRouteKafka(kafkaDeliveryRouteToHub.toApplication());
    }

    @KafkaListener(
        topics = "hub-to-delivery-route-events",
        containerFactory = "kafkaFromToHubListDtoContainerFactory"
    )
    public void handleHubToHubRouteToDeliveryResponse(KafkaDeliveryRouteToHub kafkaDeliveryRouteToHub) {
        log.info("허브 라우트 리스트 응답 수신: {}", kafkaDeliveryRouteToHub);
        hubRouteService.deliveryRouteToHubRouteKafka(kafkaDeliveryRouteToHub.toApplication());
    }

}
