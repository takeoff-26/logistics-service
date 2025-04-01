package takeoff.logistics_service.msa.user.presentation.dto.response;

import java.util.UUID;
import lombok.Builder;
import takeoff.logistics_service.msa.user.domain.entity.Employee;
import takeoff.logistics_service.msa.user.domain.entity.User;

@Builder
public record GetUserListInfoDto(
        Long userId,
        String username,
        String slackEmail,
        String role,
        String companyId,
        String  hubId
) {
    public static GetUserListInfoDto from(User user) {
        if (user instanceof Employee employee) {
            return new GetUserListInfoDto(
                    user.getId(),
                    user.getUsername(),
                    user.getSlackEmail(),
                    user.getRole().name(),
                    employee.getCompanyIdAsString(),
                    employee.getHubIdAsString()
            );
        }
        return new GetUserListInfoDto(
                user.getId(),
                user.getUsername(),
                user.getSlackEmail(),
                user.getRole().name(),
                null,
                null
        );
    }
}