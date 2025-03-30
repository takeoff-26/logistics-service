package takeoff.logistics_service.msa.hub.hub.application.service.kafka;

/**
 * @author : hanjihoon
 * @Date : 2025. 03. 31.
 */
public interface HubEventProducer {
    void sendToHubRoute(Object event);

}
