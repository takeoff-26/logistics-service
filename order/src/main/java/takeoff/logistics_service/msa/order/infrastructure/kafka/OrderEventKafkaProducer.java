package takeoff.logistics_service.msa.order.infrastructure.kafka;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import takeoff.logistics_service.msa.order.application.client.dto.request.PostDeliveryRequestDto;
import takeoff.logistics_service.msa.order.application.service.kafka.OrderEventProducer;

/**
 * @author : hanjihoon
 * @Date : 2025. 03. 30.
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class OrderEventKafkaProducer implements OrderEventProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final String deliveryTopicName;
    private final String companyTopicName;

    @Override
    public void sendToDelivery(PostDeliveryRequestDto event) {
        kafkaTemplate.send(deliveryTopicName, event);
        log.info(deliveryTopicName , ": 이벤트 발행");
    }

    @Override
    public void sendToCompany(UUID companyId) {
        kafkaTemplate.send(companyTopicName, companyId);
        log.info(companyTopicName + ": 이벤트 발행");
    }
}
