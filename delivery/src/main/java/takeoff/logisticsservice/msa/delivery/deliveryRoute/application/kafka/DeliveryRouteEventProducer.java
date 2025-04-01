package takeoff.logisticsservice.msa.delivery.deliveryRoute.application.kafka;

import takeoff.logisticsservice.msa.delivery.deliveryRoute.application.client.dto.request.PostHubRouteRequestDto;

/**
 * @author : hanjihoon
 * @Date : 2025. 04. 02.
 */
public interface DeliveryRouteEventProducer {

    void sendToHub(PostHubRouteRequestDto from);
}
