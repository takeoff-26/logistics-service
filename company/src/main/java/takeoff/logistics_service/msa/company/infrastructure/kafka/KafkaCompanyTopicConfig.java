package takeoff.logistics_service.msa.company.infrastructure.kafka;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author : hanjihoon
 * @Date : 2025. 03. 30.
 */
@Configuration
public class KafkaCompanyTopicConfig {

    private static final String ORDER_TOPIC_NAME = "order-events";
    private static final String DELIVERY_TOPIC_NAME = "delivery-events";
    private static final String COMPANY_TOPIC_NAME = "company-events";

    private static final String COMPANY_TO_ORDER_TOPIC_NAME = "company-to-order-events";
    private static final String COMPANY_TO_DELIVERY_TOPIC_NAME = "company-to-delivery-events";
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
    public NewTopic companyToOrderTopic() {
        return new NewTopic("company-to-order-events", 3, (short) 1);
    }

    @Bean
    public String companyToOrderTopicName() {
        return COMPANY_TO_ORDER_TOPIC_NAME;
    }

    @Bean
    public NewTopic companyToDeliveryTopic() {
        return new NewTopic("company-to-delivery-events", 3, (short) 1);
    }

    @Bean
    public String companyToDeliveryTopicName() {
        return COMPANY_TO_DELIVERY_TOPIC_NAME;
    }

}
