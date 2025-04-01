package takeoff.logisticsservice.msa.delivery.deliveryRoute.infrastructure.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import takeoff.logisticsservice.msa.delivery.delivery.application.dto.kafka.KafkaCompanyToDeliveryDto;
import takeoff.logisticsservice.msa.delivery.delivery.application.dto.kafka.KafkaDeliverySequenceDto;
import takeoff.logisticsservice.msa.delivery.delivery.application.dto.kafka.KafkaDeliveryToCompany;
import takeoff.logisticsservice.msa.delivery.delivery.application.dto.kafka.KafkaOrderUpdateDto;
import takeoff.logisticsservice.msa.delivery.delivery.application.kafka.DeliveryEventProducer;
import takeoff.logisticsservice.msa.delivery.deliveryRoute.application.client.dto.request.PostHubRouteRequestDto;
import takeoff.logisticsservice.msa.delivery.deliveryRoute.application.kafka.DeliveryRouteEventProducer;


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
    public void sendToHub(PostHubRouteRequestDto postHubRouteRequestDto) {
        kafkaTemplate.send(deliveryRouteToHubTopicName, postHubRouteRequestDto);
        log.info(deliveryRouteToHubTopicName + ": 이벤트 발행");
    }
}
