package takeoff.logisticsservice.msa.delivery.deliveryRoute.infrastructure.kafka;

import java.util.HashMap;
import java.util.Map;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import takeoff.logisticsservice.msa.delivery.deliveryRoute.application.kafka.dto.KafkaHubRouteResponseDto;
import takeoff.logisticsservice.msa.delivery.deliveryRoute.infrastructure.kafka.dto.KafkaDeliveryToDeliveryRoute;
import takeoff.logisticsservice.msa.delivery.deliveryRoute.infrastructure.kafka.serializer.DtoDeserializer;


/**
 * @author : hanjihoon
 * @Date : 2025. 03. 30.
 */
@EnableKafka
@Configuration
public class KafkaDeliveryRouteConsumerConfig {

    @Bean
    public <T> ConsumerFactory<String, T> consumerDeliveryRouteFactory(Class<T> targetType) {
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
    public ConsumerFactory<String, KafkaDeliveryToDeliveryRoute> kafkaDeliveryToDeliveryRouteConsumerFactory() {
        return consumerDeliveryRouteFactory(KafkaDeliveryToDeliveryRoute.class);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, KafkaDeliveryToDeliveryRoute> kafkaDeliveryToDeliveryRouteContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, KafkaDeliveryToDeliveryRoute> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(kafkaDeliveryToDeliveryRouteConsumerFactory());
        return factory;
    }

    @Bean
    public ConsumerFactory<String, KafkaHubRouteResponseDto> kafkaHubRouteResponseDtoConsumerFactory() {
        return consumerDeliveryRouteFactory(KafkaHubRouteResponseDto.class);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, KafkaHubRouteResponseDto> kafkaHubRouteResponseDtoContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, KafkaHubRouteResponseDto> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(kafkaHubRouteResponseDtoConsumerFactory());
        return factory;
    }
}
