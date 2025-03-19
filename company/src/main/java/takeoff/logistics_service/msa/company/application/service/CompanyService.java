package takeoff.logistics_service.msa.company.application.service;

import java.util.UUID;
import takeoff.logistics_service.msa.company.application.dto.request.PatchCompanyRequestDto;
import takeoff.logistics_service.msa.company.application.dto.request.PostCompanyRequestDto;
import takeoff.logistics_service.msa.company.application.dto.request.SearchCompanyRequestDto;
import takeoff.logistics_service.msa.company.application.dto.response.GetCompanyResponseDto;
import takeoff.logistics_service.msa.company.application.dto.response.PaginatedResultDto;
import takeoff.logistics_service.msa.company.application.dto.response.PatchCompanyResponseDto;
import takeoff.logistics_service.msa.company.application.dto.response.PostCompanyResponseDto;

public interface CompanyService {

	PostCompanyResponseDto saveCompany(PostCompanyRequestDto applicationDto);

	PatchCompanyResponseDto updateCompany(UUID companyId, PatchCompanyRequestDto requestDto);

	GetCompanyResponseDto findCompany(UUID companyId);

	void deleteCompany(UUID companyId, Long userId);

	PaginatedResultDto<GetCompanyResponseDto> searchCompany(SearchCompanyRequestDto applicationDto);
}
