package takeoff.logistics_service.msa.product.product.infrastructure.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import takeoff.logistics_service.msa.product.product.infrastructure.client.dto.request.PostStockRequest;
import takeoff.logistics_service.msa.product.product.infrastructure.client.dto.response.PostStockResponse;

@FeignClient(name = "stock", url = "http://localhost:19001")
public interface FeignStockClient {

	@PostMapping("/api/v1/app/stock")
	PostStockResponse saveStock(@RequestBody PostStockRequest requestDto);
}
