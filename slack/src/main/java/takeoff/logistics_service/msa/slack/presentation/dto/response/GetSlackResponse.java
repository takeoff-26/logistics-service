package takeoff.logistics_service.msa.slack.presentation.dto.response;

import java.util.UUID;
import lombok.Builder;
import takeoff.logistics_service.msa.slack.application.dto.response.GetSlackResponseDto;
import takeoff.logistics_service.msa.slack.model.entity.Slack;

/**
 * @author : hanjihoon
 * @Date : 2025. 03. 13.
 */
@Builder
public record GetSlackResponse(UUID slackId,
                               Long userId,
                               GetContentsResponse getContentsResponse) {


    public static GetSlackResponse from(GetSlackResponseDto slackResponseDto) {
        return GetSlackResponse.builder()
            .slackId(slackResponseDto.slackId())
            .userId(slackResponseDto.userId())
            .getContentsResponse(slackResponseDto.getContentsResponse())
            .build();
    }


}
