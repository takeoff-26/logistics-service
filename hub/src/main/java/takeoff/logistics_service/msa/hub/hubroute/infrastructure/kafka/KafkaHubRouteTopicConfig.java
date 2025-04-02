package takeoff.logistics_service.msa.hub.hubroute.infrastructure.kafka;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author : hanjihoon
 * @Date : 2025. 03. 30.
 */
@Configuration
public class KafkaHubRouteTopicConfig {

    private static final String HUB_ROUTE_TOPIC_NAME = "hubRoute-events";
    private static final String HUB_TOPIC_NAME = "hub-events";
    private static final String DELIVERY_ROUTE_TO_HUB_TOPIC_NAME = "delivery-route-to-hub-route-to-hub-events";
    private static final String HUB_TO_DELIVERY_ROUTE = "hub-to-delivery-route-events";
    @Bean
    public NewTopic hubTopic() {
        return new NewTopic("hub-events", 3, (short) 1);
    }
    @Bean
    public String hubTopicName() {
        return HUB_TOPIC_NAME;
    }
    @Bean
    public NewTopic hubRouteTopic() {
        return new NewTopic("hubRoute-events", 3, (short) 1);
    }

    @Bean
    public String hubRouteTopicName() {
        return HUB_ROUTE_TOPIC_NAME;
    }

    @Bean
    public NewTopic deliveryRouteHubRouteTopic() {
        return new NewTopic("delivery-route-to-hub-route-to-hub-events", 3, (short) 1);
    }
    @Bean
    public String deliveryRouteHubRouteTopicName() {
        return DELIVERY_ROUTE_TO_HUB_TOPIC_NAME;
    }

    @Bean
    public NewTopic hubToDeliveryRouteTopic() {
        return new NewTopic("hub-to-delivery-route-events", 3, (short) 1);
    }
    @Bean
    public String hubToDeliveryRouteTopicName() {
        return HUB_TO_DELIVERY_ROUTE;
    }

}
