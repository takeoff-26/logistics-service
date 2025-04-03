package takeoff.logistics_service.msa.product.stock.infrastructure.kafka.event;

import java.util.UUID;
import takeoff.logistics_service.msa.product.stock.application.dto.request.PostStockRequestDto;
import takeoff.logistics_service.msa.product.stock.application.dto.request.StockIdRequestDto;

public record ProductCreatedEventPayload(UUID productId, UUID hubId, Integer quantity) {

	public PostStockRequestDto toApplicationDto(){
		return new PostStockRequestDto(new StockIdRequestDto(productId, hubId), quantity);
	}
}