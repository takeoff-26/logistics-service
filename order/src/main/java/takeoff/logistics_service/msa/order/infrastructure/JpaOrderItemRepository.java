package takeoff.logistics_service.msa.order.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;
import takeoff.logistics_service.msa.order.model.entity.OrderItem;
import takeoff.logistics_service.msa.order.model.repository.OrderItemRepository;

public interface JpaOrderItemRepository extends JpaRepository<OrderItem, Long>,
    OrderItemRepository {

}
