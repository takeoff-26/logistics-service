package takeoff.logistics_service.msa.product.product.infrastructure.client.dto.response;

import java.time.LocalDateTime;
import takeoff.logistics_service.msa.product.product.application.dto.response.PostStockResponseDto;
import takeoff.logistics_service.msa.product.product.infrastructure.client.dto.StockIdDto;

public record PostStockResponse(StockIdDto stockId, Integer quantity, LocalDateTime createdAt) {

	public PostStockResponseDto toApplicationDto(){
		return PostStockResponseDto.builder()
			.productId(stockId.productId())
			.hubId(stockId.hubId())
			.quantity(quantity)
			.createdAt(createdAt).build();
	}
}
