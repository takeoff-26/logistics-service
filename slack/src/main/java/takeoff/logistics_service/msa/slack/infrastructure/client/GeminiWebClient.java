package takeoff.logistics_service.msa.slack.infrastructure.client;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.annotation.PostConstruct;
import java.util.Collections;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import takeoff.logistics_service.msa.slack.infrastructure.client.GeminiWebClient.RequestBody.Content;
import takeoff.logistics_service.msa.slack.infrastructure.client.GeminiWebClient.RequestBody.Part;
import takeoff.logistics_service.msa.slack.presentation.dto.request.PostSlackMessageRequestDto;

@Component
@RequiredArgsConstructor
@Slf4j
public class GeminiWebClient {

    private final WebClient webClient;

    @Value("${ai.api.key}")
    private String apiKey;

    @Value("${ai.api.url}")
    private String aiUrl;

//    해산물월드의 주문(주문번호: 1) 건에 대한 배송 계획을 수립하기 위해서는 각 경유지와 도착지까지의 이동 시간을 정확히 알아야 합니다.  운송 수단(화물차 종류, 도로 상황 등)에 따라 배송 시간이 크게 달라지기 때문입니다.  \n\n**정확한 출발 시간 산정을 위해서는 다음 정보가 필요합니다:**\n\n* **운송 수단:** 어떤 차량으로 배송할 예정인가요? (예: 1톤 트럭, 2.5톤 트럭, 냉동탑차 등)\n* **예상 운행 속도:** 평균 시속은 얼마로 예상하시나요? (교통 상황을 고려하여 실제 운행 속도보다 다소 여유 있게 설정하는 것이 좋습니다)\n* **경유지에서의 소요 시간:** 각 경유지(대전, 부산)에서의 하역 및 재적재 시간은 얼마나 소요될 것으로 예상하시나요? (예: 각 경유지 당 30분)\n* **휴식 시간:** 운전자의 휴식 시간은 어떻게 계획하시나요? (법적으로 정해진 휴식 시간을 준수해야 합니다)\n\n위 정보를 알려주시면, 고양시 출발 시각을 산출하여 김말숙님께 (msk@seafood.world) 알려드리겠습니다.  예상 소요 시간을 추정하여 답변 드릴 수는 있지만, 예상치 못한 교통 체증 등 변수를 고려하여 충분한 여유 시간을 두고 출발하는 것이 좋습니다.\n"
    private static final String ORDER_NUMBER = "주문 번호: ";
    private static final String COMPANY_NAME = "주문자 정보: ";
    private static final String PRODUCT_INFO = "상품 정보: ";
    private static final String ORDER_REQUEST = "요청 사항: ";
    private static final String FROM_HUB_NAME = "발송지: ";
    private static final String STOP_OVER_HUB_NAMES = "경유지: ";
    private static final String TO_HUB_NAME = "도착지: ";
    private static final String DELIVERY_USERS = "배송 담당자: ";
    private static final String PROMPT_INSTRUCTION = "요청 사항 시간에 맞춰 도착하기 위해 출발 해야할 시간을 대략적으로 알려주세요. ";

    public Mono<String> sendRequestToGemini(PostSlackMessageRequestDto requestDto) {
        // 필요한 파라미터들 추출
        String prompt = generatePrompt(requestDto);
        log.info(aiUrl + apiKey, "{요청 url}");
        String url = aiUrl + apiKey;

        return webClient.post()
            .uri(url)
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .body(BodyInserters.fromValue(generateRequestBody(prompt))) // 요청 본문 설정
            .retrieve()
            .bodyToMono(String.class) // 응답 처리
            .doOnError(error -> log.error("Gemini 응답에 에러가 있습니다: {}", error.getMessage(), error))
            .map(response -> {
                // AI 응답에서 발송 시한을 추출하는 부분
                String deadline = extractDeadline(response);
                log.info("도출된 발송 시한: {}", deadline);

                // 발송 시한을 포함하여 주문 정보를 반환
                return generateResultMessage(requestDto, deadline);
            });
    }

    private String generatePrompt(PostSlackMessageRequestDto requestDto) {
        StringBuilder promptBuilder = new StringBuilder();
        mappingPrompt(requestDto, promptBuilder);
        promptBuilder.append("\n").append(PROMPT_INSTRUCTION);

        return promptBuilder.toString();
    }

    private String generateResultMessage(PostSlackMessageRequestDto requestDto, String deadline) {
        StringBuilder promptBuilder = new StringBuilder();
        mappingPrompt(requestDto, promptBuilder);

        promptBuilder.append("\n").append("도출된 출발 시간은 ").append(deadline).append(" 입니다.");
        return promptBuilder.toString();
    }


    public static class RequestBody {
        private final List<Content> contents;

        @JsonCreator
        public RequestBody(@JsonProperty("contents") List<Content> contents) {
            this.contents = contents;
        }

        public List<Content> getContents() {
            return contents;
        }

        public static class Content {
            private final List<Part> parts;

            @JsonCreator
            public Content(@JsonProperty("parts") List<Part> parts) {
                this.parts = parts;
            }

            public List<Part> getParts() {
                return parts;
            }
        }

        public static class Part {
            private final String text;

            @JsonCreator
            public Part(@JsonProperty("text") String text) {
                this.text = text;
            }

            public String getText() {
                return text;
            }
        }
    }

    private RequestBody generateRequestBody(String prompt) {
        Part part = new Part(prompt);
        Content content = new Content(Collections.singletonList(part));
        return new RequestBody(Collections.singletonList(content));
    }


    public String extractDeadline(String aiResponse) {
        // AI 응답에서 "출발 시간은" 부분을 찾아서 해당 값을 추출
        String pattern = "출발 시간은 ";
        int startIdx = aiResponse.indexOf(pattern);
        if (startIdx != -1) {
            int endIdx = aiResponse.indexOf("입니다.", startIdx);
            if (endIdx != -1) {
                return aiResponse.substring(startIdx + pattern.length(), endIdx);
            }
        }
        return "출발 시간을 계산할 수 없습니다.";
    }

    private static void mappingPrompt(PostSlackMessageRequestDto requestDto, StringBuilder promptBuilder) {
        promptBuilder.append(ORDER_NUMBER).append(requestDto.orderNumber()).append("\n");
        promptBuilder.append(COMPANY_NAME).append(requestDto.companyName()).append("\n");
        promptBuilder.append(PRODUCT_INFO).append(requestDto.productInfo()).append("\n");
        promptBuilder.append(ORDER_REQUEST).append(requestDto.orderRequest()).append("\n");
        promptBuilder.append(FROM_HUB_NAME).append(requestDto.fromHubName()).append("\n");
        promptBuilder.append(STOP_OVER_HUB_NAMES).append(String.join(", ", requestDto.stopoverHubNames().hubNames())).append("\n");
        promptBuilder.append(TO_HUB_NAME).append(requestDto.toHubName()).append("\n");
        promptBuilder.append(DELIVERY_USERS).append(String.join(", ", requestDto.deliveryUsers().deliveryUserNames())).append("\n");
    }
}
