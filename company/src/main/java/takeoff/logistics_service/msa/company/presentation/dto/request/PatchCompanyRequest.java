package takeoff.logistics_service.msa.company.presentation.dto.request;

import jakarta.validation.constraints.NotNull;
import java.util.UUID;
import takeoff.logistics_service.msa.company.application.dto.request.PatchCompanyRequestDto;

public record PatchCompanyRequest(
	@NotNull String companyName, @NotNull String companyType,
	@NotNull UUID hubId, @NotNull String address) {

	public PatchCompanyRequestDto toApplicationDto() {
		return new PatchCompanyRequestDto(companyName, companyType, hubId, address);
	}
}
