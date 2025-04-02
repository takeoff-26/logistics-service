package takeoff.logisticsservice.msa.delivery.delivery.infrastructure.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import takeoff.logisticsservice.msa.delivery.delivery.application.DeliveryService;
import takeoff.logisticsservice.msa.delivery.delivery.infrastructure.kafka.dto.KafkaCompanyToDelivery;
import takeoff.logisticsservice.msa.delivery.delivery.infrastructure.kafka.dto.KafkaDeliveryIdAndCompanyIdListener;
import takeoff.logisticsservice.msa.delivery.delivery.infrastructure.kafka.dto.KafkaOrderToDelivery;

/**
 * @author : hanjihoon
 * @Date : 2025. 03. 31.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class DeliveryKafkaListener {

    private final DeliveryService deliveryService;

    @KafkaListener(
        topics = "delivery-to-sequence-events",
        containerFactory = "kafkaDeliveryIdAndCompanyIdListenerContainerFactory"
    )
    public void handleHubRouteListResponse(KafkaDeliveryIdAndCompanyIdListener event) {
        log.info("딜리버리 응답 수신: {}", event);
        deliveryService.updateDeliveryToDeliveryCompanyManager(event.toApplication());
    }

    @KafkaListener(
        topics = "delivery-events",
        containerFactory = "kafkaOrderToDeliveryContainerFactory"
    )
    public void handleHubRouteListResponse(KafkaOrderToDelivery kafkaOrderToDelivery) {
        log.info("딜리버리 응답 수신: {}", kafkaOrderToDelivery);
        deliveryService.saveDeliveryKafka(kafkaOrderToDelivery.toApplication());
    }

    @KafkaListener(
        topics = "company-to-delivery-events",
        containerFactory = "kafkaCompanyToDeliveryContainerFactory"
    )
    public void handleCompanyToDeliveryResponse(KafkaCompanyToDelivery kafkaOrderToDelivery) {
        log.info("딜리버리 응답 수신: {}", kafkaOrderToDelivery);
        deliveryService.updateDeliveryToHubIdAndFromHubId(kafkaOrderToDelivery.toApplication());
    }

}
