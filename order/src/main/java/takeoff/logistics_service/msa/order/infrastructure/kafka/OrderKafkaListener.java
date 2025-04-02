package takeoff.logistics_service.msa.order.infrastructure.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import takeoff.logistics_service.msa.order.application.service.OrderService;
import takeoff.logistics_service.msa.order.infrastructure.kafka.dto.KafkaCompany;
import takeoff.logistics_service.msa.order.infrastructure.kafka.dto.KafkaUpdateOrderDeliveryId;

/**
 * @author : hanjihoon
 * @Date : 2025. 03. 31.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class OrderKafkaListener {

    private final OrderService orderService;

    @KafkaListener(
        topics = "company-to-order-events",
        containerFactory = "UUIDContainerFactory"
    )
    public void handleCompanyToOrderResponse(KafkaCompany kafkaCompany) {
        log.info("허브 라우트 리스트 응답 수신: {}", kafkaCompany);
        orderService.updateOrderToHubId(kafkaCompany.toApplication());
    }

    @KafkaListener(
        topics = "delivery-to-update-order-events",
        containerFactory = "KafkaUpdateOrderDeliveryIdContainerFactory"
    )
    public void handleDeliveryIdUpdateResponse(KafkaUpdateOrderDeliveryId kafkaUpdateOrderDeliveryId) {
        log.info("허브 라우트 리스트 응답 수신: {}", kafkaUpdateOrderDeliveryId);
        orderService.updateOrder(kafkaUpdateOrderDeliveryId.toApplication());
    }


}
