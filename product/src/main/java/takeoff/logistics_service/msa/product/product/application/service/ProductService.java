package takeoff.logistics_service.msa.product.product.application.service;

import takeoff.logistics_service.msa.product.product.application.dto.request.PostProductRequestDto;
import takeoff.logistics_service.msa.product.product.application.dto.response.PostProductResponseDto;

public interface ProductService {

	PostProductResponseDto saveProduct(PostProductRequestDto requestDto);

//	PostProductResponse saveProduct(
//		PostProductRequest requestDto);
//
//	PatchProductResponse updateProductName(PatchProductRequestDto requestDto);
//
//	void deleteProduct(UUID productId);
}
