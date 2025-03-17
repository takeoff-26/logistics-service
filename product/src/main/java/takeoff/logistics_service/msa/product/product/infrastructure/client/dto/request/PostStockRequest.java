package takeoff.logistics_service.msa.product.product.infrastructure.client.dto.request;

import takeoff.logistics_service.msa.product.product.application.dto.request.PostStockRequestDto;
import takeoff.logistics_service.msa.product.product.infrastructure.client.dto.StockIdDto;

public record PostStockRequest(StockIdDto stockId, Integer quantity) {

	public static PostStockRequest from(PostStockRequestDto requestDto) {
		return new PostStockRequest(
			StockIdDto.from(requestDto.productId(), requestDto.hubId()), requestDto.quantity());
	}
}

