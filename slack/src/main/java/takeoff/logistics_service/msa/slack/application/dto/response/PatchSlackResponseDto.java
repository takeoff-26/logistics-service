package takeoff.logistics_service.msa.slack.application.dto.response;

import static takeoff.logistics_service.msa.slack.model.entity.QSlack.slack;

import java.util.UUID;
import lombok.Builder;
import takeoff.logistics_service.msa.slack.model.entity.Slack;
import takeoff.logistics_service.msa.slack.presentation.dto.response.PatchContentsResponse;
import takeoff.logistics_service.msa.slack.presentation.dto.response.PatchSlackResponse;

/**
 * @author : hanjihoon
 * @Date : 2025. 03. 13.
 */
@Builder
public record PatchSlackResponseDto(UUID slackId,
                                    Long userId,
                                    PatchContentsResponseDto patchContentsResponseDto) {

    public static PatchSlackResponseDto from(Slack slack) {
        return PatchSlackResponseDto.builder()
            .slackId(slack.getId())
            .userId(slack.getUserId())
            .patchContentsResponseDto(PatchContentsResponseDto.from(slack.getContents()))
            .build();
    }

}
