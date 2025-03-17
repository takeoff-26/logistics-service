package takeoff.logistics_service.msa.slack.infrastructure.persistence;

import takeoff.logistics_service.msa.slack.model.repository.search.PaginatedResult;
import takeoff.logistics_service.msa.slack.model.repository.search.SlackSearchCriteria;
import takeoff.logistics_service.msa.slack.model.repository.search.SlackSearchCriteriaResponse;

/**
 * @author : hanjihoon
 * @Date : 2025. 03. 14.
 */
public interface JpaSlackRepositoryCustom {
    PaginatedResult<SlackSearchCriteriaResponse> searchSlack(SlackSearchCriteria slackSearchCriteria);

}
