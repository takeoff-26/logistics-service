package takeoff.logisticsservice.msa.delivery.deliveryRoute.application.kafka;

import takeoff.logisticsservice.msa.delivery.deliveryRoute.application.kafka.dto.KafkaHubRouteRequestDto;

/**
 * @author : hanjihoon
 * @Date : 2025. 04. 02.
 */
public interface DeliveryRouteEventProducer {

    void sendToHub(KafkaHubRouteRequestDto from);
}
