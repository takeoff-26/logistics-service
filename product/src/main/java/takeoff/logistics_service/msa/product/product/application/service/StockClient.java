package takeoff.logistics_service.msa.product.product.application.service;

import takeoff.logistics_service.msa.product.product.application.dto.request.PostStockRequestDto;
import takeoff.logistics_service.msa.product.product.application.dto.response.PostStockResponseDto;

public interface StockClient {
	PostStockResponseDto saveStock(PostStockRequestDto postStockRequestDto);
}
