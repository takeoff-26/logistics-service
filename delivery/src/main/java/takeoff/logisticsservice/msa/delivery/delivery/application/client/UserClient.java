package takeoff.logisticsservice.msa.delivery.delivery.application.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import takeoff.logisticsservice.msa.delivery.delivery.application.client.dto.response.GetUserResponseDto;

@Component
@FeignClient(name = "userDeliveryInternal")
public interface UserClient {

  @GetMapping
  GetUserResponseDto findByUserId(@RequestParam("userId") Long userId);

}
