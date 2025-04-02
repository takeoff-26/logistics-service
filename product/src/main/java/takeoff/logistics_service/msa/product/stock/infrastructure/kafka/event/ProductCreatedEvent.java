package takeoff.logistics_service.msa.product.stock.infrastructure.kafka.event;

import takeoff.logistics_service.msa.common.domain.UserInfoDto;
import takeoff.logistics_service.msa.product.stock.application.event.EventType;

public record ProductCreatedEvent(
	EventType eventType, ProductCreatedEventPayload payload, UserInfoDto userInfo) implements ProductEvent {
}
