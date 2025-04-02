package takeoff.logisticsservice.msa.delivery.deliveryRoute.infrastructure.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import takeoff.logisticsservice.msa.delivery.deliveryRoute.application.kafka.DeliveryRouteEventProducer;
import takeoff.logisticsservice.msa.delivery.deliveryRoute.application.kafka.dto.KafkaHubRouteRequestDto;


/**
 * @author : hanjihoon
 * @Date : 2025. 03. 30.
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class DeliveryRouteEventKafkaProducer implements DeliveryRouteEventProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final String deliveryRouteToHubTopicName;

    @Override
    public void sendToHub(KafkaHubRouteRequestDto kafkaHubRouteRequestDto) {
        kafkaTemplate.send(deliveryRouteToHubTopicName, kafkaHubRouteRequestDto);
        log.info(deliveryRouteToHubTopicName + ": 이벤트 발행");
    }
}
