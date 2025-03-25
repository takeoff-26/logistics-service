package takeoff.logistics_service.msa.user.presentation.dto.response;

import lombok.Builder;
import takeoff.logistics_service.msa.user.domain.entity.Employee;
import takeoff.logistics_service.msa.user.domain.vo.CompanyId;
import takeoff.logistics_service.msa.user.domain.vo.HubId;

import java.util.UUID;
@Builder
public record GetManagerListInfoDto(
        Long userId,
        String username,
        String slackEmail,
        String role,
        String companyId,
        String  hubId
) {
    public static GetManagerListInfoDto from(Employee employee) {
        return GetManagerListInfoDto.builder()
                .userId(employee.getId())
                .username(employee.getUsername())
                .slackEmail(employee.getSlackEmail())
                .role(employee.getRole().name())
                .companyId(employee.getCompanyIdAsString())
                .hubId(employee.getHubIdAsString())
                .build();
    }
}
