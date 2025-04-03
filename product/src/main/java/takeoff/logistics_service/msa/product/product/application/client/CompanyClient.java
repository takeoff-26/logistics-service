package takeoff.logistics_service.msa.product.product.application.client;

import java.util.UUID;

public interface CompanyClient {

	void findByCompanyId(UUID companyId);
}