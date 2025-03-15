package takeoff.logistics_service.msa.slack.presentation.dto.request;


import jakarta.validation.constraints.NotNull;
import takeoff.logistics_service.msa.slack.model.entity.Slack;

/**
 * @author : hanjihoon
 * @Date : 2025. 03. 13.
 */

public record PostSlackRequestDto(@NotNull Long userId,
                                  @NotNull PostContentsRequestDto postContentsRequestDto) {

    public Slack toEntity() {
        return Slack.builder()
            .userId(userId)
            .contents(postContentsRequestDto.toVo())
            .build();
    }

}
