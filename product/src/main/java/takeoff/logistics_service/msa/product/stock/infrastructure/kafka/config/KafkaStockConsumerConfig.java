package takeoff.logistics_service.msa.product.stock.infrastructure.kafka.config;

import java.util.HashMap;
import java.util.Map;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import takeoff.logistics_service.msa.product.stock.infrastructure.kafka.event.ProductCreatedEvent;
import takeoff.logistics_service.msa.product.stock.infrastructure.kafka.event.ProductEvent;

@EnableKafka
@Configuration
@ComponentScan("takeoff.logistics_service.msa.product.stock")
public class KafkaStockConsumerConfig {

	@Value("${spring.kafka.bootstrap-servers}")
	private String bootstrapServers;

	@Bean
	public <T> ConsumerFactory<String, T> stockConsumerFactory(Class<T> targetType) {
		Map<String, Object> props = new HashMap<>();
		props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
		props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
		props.put(ConsumerConfig.GROUP_ID_CONFIG, "stock-service");

		JsonDeserializer<T> jsonDeserializer = new JsonDeserializer<>(targetType);
		jsonDeserializer.setUseTypeHeaders(false);
		jsonDeserializer.setRemoveTypeHeaders(true);

		return new DefaultKafkaConsumerFactory<>(
			props,
			new StringDeserializer(),
			jsonDeserializer
		);
	}

	@Bean
	public ConsumerFactory<String, ProductCreatedEvent> productCreatedEventConsumerFactory() {
		return stockConsumerFactory(ProductCreatedEvent.class);
	}

	@Bean
	public ConcurrentKafkaListenerContainerFactory<String, ProductCreatedEvent>
	productCreatedEventKafkaListenerContainerFactory() {
		ConcurrentKafkaListenerContainerFactory<String, ProductCreatedEvent> factory =
			new ConcurrentKafkaListenerContainerFactory<>();
		factory.setConsumerFactory(productCreatedEventConsumerFactory());
		return factory;
	}
}
