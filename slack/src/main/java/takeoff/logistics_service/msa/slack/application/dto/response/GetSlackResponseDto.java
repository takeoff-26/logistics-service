package takeoff.logistics_service.msa.slack.application.dto.response;

import java.util.UUID;
import lombok.Builder;
import takeoff.logistics_service.msa.slack.model.entity.Slack;
import takeoff.logistics_service.msa.slack.presentation.dto.response.GetContentsResponse;

/**
 * @author : hanjihoon
 * @Date : 2025. 03. 13.
 */
@Builder
public record GetSlackResponseDto(UUID slackId,
                                  Long userId,
                                  GetContentsResponse getContentsResponse) {

    public static GetSlackResponseDto from(Slack slack) {
        return GetSlackResponseDto.builder()
            .slackId(slack.getId())
            .userId(slack.getUserId())
            .getContentsResponse(GetContentsResponse.from(slack.getContents()))
            .build();
    }

}
