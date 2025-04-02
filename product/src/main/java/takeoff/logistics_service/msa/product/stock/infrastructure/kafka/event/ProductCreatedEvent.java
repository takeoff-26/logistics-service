package takeoff.logistics_service.msa.product.stock.infrastructure.kafka.event;

import static takeoff.logistics_service.msa.product.stock.application.event.EventType.CREATED;

import takeoff.logistics_service.msa.common.domain.UserInfoDto;
import takeoff.logistics_service.msa.product.stock.application.dto.request.PostStockRequestDto;
import takeoff.logistics_service.msa.product.stock.application.event.EventType;

public record ProductCreatedEvent(
	EventType eventType, PostStockRequestDto payload, UserInfoDto userInfo) implements ProductEvent {

	public static ProductCreatedEvent of(PostStockRequestDto payload, UserInfoDto userInfo) {
		return new ProductCreatedEvent(CREATED, payload, userInfo);
	}
}
