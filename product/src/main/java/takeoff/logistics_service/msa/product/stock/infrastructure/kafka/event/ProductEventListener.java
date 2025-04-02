package takeoff.logistics_service.msa.product.stock.infrastructure.kafka.event;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import takeoff.logistics_service.msa.product.stock.application.service.StockService;

@Component
@RequiredArgsConstructor
public class ProductEventListener {

	private final StockService stockService;

	//토픽은 이벤트타입을 그냥 명시해서 사용하는게 나을듯
	//필터시 타입 다르면 역직렬화 문제가 발생할 수 있음
	@KafkaListener(
		topics = "product-created-events",
		containerFactory = "productCreatedEventKafkaListenerContainerFactory"
	)
	public void handleProductCreated(ProductCreatedEvent productEvent) {
		stockService.create(productEvent.payload().toApplicationDto(), productEvent.userInfo());
	}
}
