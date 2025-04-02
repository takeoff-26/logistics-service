package takeoff.logistics_service.msa.product.stock.infrastructure.kafka.event;

import static takeoff.logistics_service.msa.product.stock.application.event.EventType.CREATED;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import takeoff.logistics_service.msa.product.stock.application.service.StockService;

@Component
@RequiredArgsConstructor
public class ProductEventListener {

	private final StockService stockService;

	//토픽은 이벤트타입을 그냥 명시해서 사용하는게 나을듯
	@KafkaListener(
		topics = "product-events",
		containerFactory = "productCreatedEventConsumerFactory"
	)
	public void handleProductCreated(ProductCreatedEvent productEvent) {
		stockService.create(productEvent.payload(), productEvent.userInfo());
	}
}
