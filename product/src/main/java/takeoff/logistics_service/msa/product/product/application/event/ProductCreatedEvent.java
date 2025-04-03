package takeoff.logistics_service.msa.product.product.application.event;

import static takeoff.logistics_service.msa.product.product.application.event.EventType.CREATED;

import takeoff.logistics_service.msa.common.domain.UserInfoDto;
import takeoff.logistics_service.msa.product.product.application.dto.request.PostStockRequestDto;

public record ProductCreatedEvent(
	EventType eventType, PostStockRequestDto payload, UserInfoDto userInfo) implements ProductEvent {

	public static ProductCreatedEvent of(PostStockRequestDto payload, UserInfoDto userInfo) {
		return new ProductCreatedEvent(CREATED, payload, userInfo);
	}
}
