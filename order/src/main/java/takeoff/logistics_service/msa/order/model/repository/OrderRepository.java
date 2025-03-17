package takeoff.logistics_service.msa.order.model.repository;

import takeoff.logistics_service.msa.order.model.entity.Order;

public interface OrderRepository {

  <T extends Order> T save(T order);
}
