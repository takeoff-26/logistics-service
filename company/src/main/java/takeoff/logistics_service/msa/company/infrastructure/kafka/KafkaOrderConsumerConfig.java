package takeoff.logistics_service.msa.company.infrastructure.kafka;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import takeoff.logistics_service.msa.company.infrastructure.kafka.dto.KafkaDeliveryToCompany;
import takeoff.logistics_service.msa.company.infrastructure.kafka.dto.KafkaOrderToCompany;
import takeoff.logistics_service.msa.company.infrastructure.kafka.serializer.DtoDeserializer;


/**
 * @author : hanjihoon
 * @Date : 2025. 03. 30.
 */
@EnableKafka
@Configuration
public class KafkaOrderConsumerConfig {

    @Bean
    public <T> ConsumerFactory<String, T> consumerOrderFactory(Class<T> targetType) {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, DtoDeserializer.class);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "saga-takeoff-group");
        return new DefaultKafkaConsumerFactory<>(
            props,
            new StringDeserializer(),
            new DtoDeserializer<>(targetType)
        );
    }
    @Bean
    public ConsumerFactory<String, KafkaOrderToCompany> KafkaOrderToCompanyConsumerFactory() {
        return consumerOrderFactory(KafkaOrderToCompany.class);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, KafkaOrderToCompany> KafkaOrderToCompanyContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, KafkaOrderToCompany> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(KafkaOrderToCompanyConsumerFactory());
        return factory;
    }

    @Bean
    public ConsumerFactory<String, KafkaDeliveryToCompany> KafkaDeliveryToCompanyConsumerFactory() {
        return consumerOrderFactory(KafkaDeliveryToCompany.class);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, KafkaDeliveryToCompany> KafkaDeliveryToCompanyContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, KafkaDeliveryToCompany> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(KafkaDeliveryToCompanyConsumerFactory());
        return factory;
    }
}
