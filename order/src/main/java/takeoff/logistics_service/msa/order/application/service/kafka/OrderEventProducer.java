package takeoff.logistics_service.msa.order.application.service.kafka;

import java.util.UUID;
import takeoff.logistics_service.msa.order.application.client.dto.request.PostDeliveryRequestDto;

/**
 * @author : hanjihoon
 * @Date : 2025. 03. 31.
 */
public interface OrderEventProducer {

    void sendToDelivery(PostDeliveryRequestDto event);

    void sendToCompany(UUID companyId);
}
