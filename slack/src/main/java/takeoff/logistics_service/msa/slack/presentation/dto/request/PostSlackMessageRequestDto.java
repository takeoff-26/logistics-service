package takeoff.logistics_service.msa.slack.presentation.dto.request;

import jakarta.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;
import takeoff.logistics_service.msa.slack.presentation.dto.DeliveryUsers;
import takeoff.logistics_service.msa.slack.presentation.dto.StopoverHubNames;

/**
 * @author : hanjihoon
 * @Date : 2025. 03. 16.
 */
public record PostSlackMessageRequestDto(@NotNull Integer orderNumber,
                                         @NotNull String companyName,
                                         @NotNull String productInfo,
                                         @NotNull String orderRequest,
                                         @NotNull String fromHubName,
                                         @NotNull StopoverHubNames stopoverHubNames,
                                         @NotNull String toHubName,
                                         @NotNull DeliveryUsers deliveryUsers,
                                         @NotNull String companyDeliveryUserName) {


}
