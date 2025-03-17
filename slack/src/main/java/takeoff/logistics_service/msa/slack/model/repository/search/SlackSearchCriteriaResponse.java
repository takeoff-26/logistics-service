package takeoff.logistics_service.msa.slack.model.repository.search;


import java.util.UUID;
import lombok.Builder;
import takeoff.logistics_service.msa.slack.model.entity.Contents;
import takeoff.logistics_service.msa.slack.model.entity.Slack;

/**
 * @author : hanjihoon
 * @Date : 2025. 03. 17.
 */
@Builder
public record SlackSearchCriteriaResponse(UUID slackId,
                                          Long userId,
                                          Contents contents) {

    public static SlackSearchCriteriaResponse from(Slack slack) {
        return SlackSearchCriteriaResponse.builder()
            .slackId(slack.getId())
            .userId(slack.getUserId())
            .contents(slack.getContents())
            .build();
    }
}
