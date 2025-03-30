package takeoff.logistics_service.msa.hub.hubroute.application.service.kafka;

/**
 * @author : hanjihoon
 * @Date : 2025. 03. 30.
 */
public interface HubRouteEventProducer {
    void sendToHub(Object event);
}

