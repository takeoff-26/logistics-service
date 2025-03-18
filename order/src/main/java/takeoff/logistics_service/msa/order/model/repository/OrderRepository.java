package takeoff.logistics_service.msa.order.model.repository;

import java.util.Optional;
import java.util.UUID;
import takeoff.logistics_service.msa.order.model.entity.Order;

public interface OrderRepository {

  Order save(Order order);

  Optional<Order> findById(UUID orderId);
}
