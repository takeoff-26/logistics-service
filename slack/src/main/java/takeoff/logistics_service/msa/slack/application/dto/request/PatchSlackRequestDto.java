package takeoff.logistics_service.msa.slack.application.dto.request;


import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import takeoff.logistics_service.msa.slack.model.entity.Slack;
import takeoff.logistics_service.msa.slack.presentation.dto.request.PatchContentsRequest;

/**
 * @author : hanjihoon
 * @Date : 2025. 03. 13.
 */
@Builder
public record PatchSlackRequestDto(Long userId,
                                   PatchContentsRequest patchContentsRequest) {


}
