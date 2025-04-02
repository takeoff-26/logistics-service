package takeoff.logisticsservice.msa.delivery.deliveryRoute.infrastructure.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import takeoff.logisticsservice.msa.delivery.deliveryRoute.application.kafka.dto.KafkaHubRouteResponseDto;
import takeoff.logisticsservice.msa.delivery.deliveryRoute.application.service.DeliveryRouteService;
import takeoff.logisticsservice.msa.delivery.deliveryRoute.infrastructure.kafka.dto.KafkaDeliveryToDeliveryRoute;

/**
 * @author : hanjihoon
 * @Date : 2025. 03. 31.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class DeliveryRouteKafkaListener {

    private final DeliveryRouteService deliveryRouteService;

    @KafkaListener(
        topics = "delivery-to-deliveryRoute_events",
        containerFactory = "kafkaDeliveryToDeliveryRouteContainerFactory"
    )
    public void handleDeliveryRouteResponse(KafkaDeliveryToDeliveryRoute kafkaDeliveryToDeliveryRoute) {
        log.info("딜리버리 라우트 리스트 응답 수신: {}", kafkaDeliveryToDeliveryRoute);
        deliveryRouteService.saveDeliveryRoutesKafka(kafkaDeliveryToDeliveryRoute);
    }
    @KafkaListener(
        topics = "hub-to-delivery-route-events",
        containerFactory = "kafkaHubRouteResponseDtoContainerFactory"
    )
    public void handleHubToDeliveryRouteResponse(KafkaHubRouteResponseDto kafkaHubRouteResponseDto) {
        log.info("딜리버리 라우트 리스트 응답 수신: {}", kafkaHubRouteResponseDto);
        deliveryRouteService.HubRouteToDelivery(kafkaHubRouteResponseDto);
    }
    //여기를 허브 라우트에서 만들어진 값을 넣어야 함


}
