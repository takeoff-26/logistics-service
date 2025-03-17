package takeoff.logistics_service.msa.slack.application.dto.response;

import java.util.UUID;
import lombok.Builder;
import takeoff.logistics_service.msa.slack.model.repository.search.SlackSearchCriteriaResponse;
import takeoff.logistics_service.msa.slack.presentation.dto.response.SearchContentsResponse;

/**
 * @author : hanjihoon
 * @Date : 2025. 03. 13.
 */
@Builder
public record SearchSlackResponseDto(UUID slackId,
                                     Long userId,
                                     SearchContentsResponse searchContentsResponse) {

    public static SearchSlackResponseDto from(SlackSearchCriteriaResponse slackSearchCriteriaResponse) {
        return SearchSlackResponseDto.builder()
            .slackId(slackSearchCriteriaResponse.slackId())
            .userId(slackSearchCriteriaResponse.userId())
            .searchContentsResponse(
                SearchContentsResponse.from(slackSearchCriteriaResponse.contents()))
            .build();
    }

}
