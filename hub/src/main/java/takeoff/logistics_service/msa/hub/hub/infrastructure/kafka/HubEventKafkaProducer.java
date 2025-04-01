package takeoff.logistics_service.msa.hub.hub.infrastructure.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import takeoff.logistics_service.msa.hub.hub.application.dto.kafka.KafkaFromToHubDto;
import takeoff.logistics_service.msa.hub.hub.application.service.kafka.HubEventProducer;


/**
 * @author : hanjihoon
 * @Date : 2025. 03. 30.
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class HubEventKafkaProducer implements HubEventProducer {

    private final KafkaTemplate<String, KafkaFromToHubDto> kafkaTemplate;

    private final String hubTopicName;
    private final String hubToDeliveryRouteTopicName;

    @Override
    public void sendToHubRoute(KafkaFromToHubDto event) {
        kafkaTemplate.send(hubTopicName, event);
        log.info(hubTopicName , ": 이벤트 발행");
    }

    @Override
    public void sendToDeliveryRoute(KafkaFromToHubDto kafka) {
        kafkaTemplate.send(hubToDeliveryRouteTopicName, kafka);
        //여기를 허브 라우트가 받아서 경로 만들어서 딜리버리 라우트로 넘겨야대
    }
}
