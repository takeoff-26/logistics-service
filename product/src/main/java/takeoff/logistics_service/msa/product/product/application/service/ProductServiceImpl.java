package takeoff.logistics_service.msa.product.product.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import takeoff.logistics_service.msa.product.product.application.dto.request.PostProductRequestDto;
import takeoff.logistics_service.msa.product.product.application.dto.request.PostStockRequestDto;
import takeoff.logistics_service.msa.product.product.application.dto.response.PostProductResponseDto;
import takeoff.logistics_service.msa.product.product.application.dto.response.PostStockResponseDto;
import takeoff.logistics_service.msa.product.product.model.entity.Product;
import takeoff.logistics_service.msa.product.product.model.repository.ProductRepository;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

	private final ProductRepository productRepository;
	private final StockClient stockClient;

	@Override
	public PostProductResponseDto saveProduct(PostProductRequestDto requestDto) {

		Product product = productRepository.save(Product.create(requestDto.toCommand()));
		PostStockResponseDto responseDto =
			stockClient.saveStock(PostStockRequestDto.from(product.getId(), requestDto));

		return PostProductResponseDto.from(product, responseDto);
	}

//	@Override
//	@Transactional
//	public PatchProductResponse updateProductName(PatchProductRequestDto requestDto) {
//		return null;
//	}
//
//	@Override
//	public void deleteProduct(UUID productId) {
//
//	}
}
