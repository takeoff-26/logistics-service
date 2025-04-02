package takeoff.logisticsservice.msa.delivery.delivery.infrastructure.kafka;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author : hanjihoon
 * @Date : 2025. 03. 30.
 */
@Configuration
public class KafkaDeliveryTopicConfig {

    private static final String ORDER_TOPIC_NAME = "order-events";
    private static final String DELIVERY_TOPIC_NAME = "delivery-events";

    private static final String DELIVERY_TO_SEQUENCE_TOPIC_NAME = "delivery-to-sequence-events";

    private static final String DELIVERY_UPDATE_TO_ORDER_TOPIC_NAME = "delivery-to-update-order-events";
    private static final String DELIVERY_TO_DELIVERY_ROUTE_TOPIC_NAME = "delivery-to-deliveryRoute_events";

    private static final String DELIVERY_SEQUENCE_TOPIC_NAME = "delivery-sequence-events";
    private static final String COMPANY_TOPIC_NAME = "company-events";

    private static final String DELIVERY_ROUTE_TO_HUB_TOPIC_NAME = "delivery-route-to-hub-events";
    private static final String DELIVERY_TO_COMPANY_TOPIC_NAME = "delivery-to-company-events";
    private static final String STOCK_TOPIC_NAME = "stock-events";
    @Bean
    public NewTopic orderTopic() {
        return new NewTopic("order-events", 3, (short) 1);
    }
    @Bean
    public String orderTopicName() {
        return ORDER_TOPIC_NAME;
    }
    @Bean
    public NewTopic deliveryTopic() {
        return new NewTopic("delivery-events", 3, (short) 1);
    }

    @Bean
    public String deliveryTopicName() {
        return DELIVERY_TOPIC_NAME;
    }

    @Bean
    public NewTopic companyTopic() {
        return new NewTopic("company-events", 3, (short) 1);
    }

    @Bean
    public String companyTopicName() {
        return COMPANY_TOPIC_NAME;
    }

    @Bean
    public NewTopic stockTopic() {
        return new NewTopic("stock-events", 3, (short) 1);
    }

    @Bean
    public String stockTopicName() {
        return STOCK_TOPIC_NAME;
    }

    @Bean
    public NewTopic deliverySequenceTopic() {
        return new NewTopic("delivery-sequence-events", 3, (short) 1);
    }

    @Bean
    public String deliverySequenceTopicName() {
        return DELIVERY_SEQUENCE_TOPIC_NAME;
    }
    @Bean
    public NewTopic deliveryToSequenceTopic() {
        return new NewTopic("delivery-to-sequence-events", 3, (short) 1);
    }

    @Bean
    public String deliveryToSequenceTopicName() {
        return DELIVERY_TO_SEQUENCE_TOPIC_NAME;
    }

    @Bean
    public NewTopic deliveryToCompanyTopic() {
        return new NewTopic("delivery-to-company-events", 3, (short) 1);
    }
    @Bean
    public String deliveryToCompanyTopicName() {
        return DELIVERY_TO_COMPANY_TOPIC_NAME;
    }

    @Bean
    public NewTopic deliveryToUpdateOrderTopic() {
        return new NewTopic("delivery-to-update-order-events", 3, (short) 1);
    }
    @Bean
    public String deliveryToUpdateOrderTopicName() {
        return DELIVERY_UPDATE_TO_ORDER_TOPIC_NAME;
    }

    @Bean
    public NewTopic deliveryToDeliveryRouteTopic() {
        return new NewTopic("delivery-to-deliveryRoute_events", 3, (short) 1);
    }
    @Bean
    public String deliveryToDeliveryRouteTopicName() {
        return DELIVERY_TO_DELIVERY_ROUTE_TOPIC_NAME;
    }

    @Bean
    public NewTopic deliveryRouteToHubRouteTopic() {
        return new NewTopic("delivery-route-to-hub-events", 3, (short) 1);
    }
    @Bean
    public String deliveryRouteToHubTopicName() {
        return DELIVERY_ROUTE_TO_HUB_TOPIC_NAME;
    }

}
