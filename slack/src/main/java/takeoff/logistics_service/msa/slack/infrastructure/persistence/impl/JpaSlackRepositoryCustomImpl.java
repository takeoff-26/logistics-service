package takeoff.logistics_service.msa.slack.infrastructure.persistence.impl;

import static takeoff.logistics_service.msa.slack.model.entity.QSlack.slack;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import takeoff.logistics_service.msa.slack.infrastructure.persistence.JpaSlackRepositoryCustom;
import takeoff.logistics_service.msa.slack.model.entity.QSlack;
import takeoff.logistics_service.msa.slack.presentation.dto.request.SlackRequestDto;
import takeoff.logistics_service.msa.slack.presentation.dto.response.SlackResponseDto;


/**
 * @author : hanjihoon
 * @Date : 2025. 03. 14.
 */
@RequiredArgsConstructor
public class JpaSlackRepositoryCustomImpl implements JpaSlackRepositoryCustom {

    private final JPAQueryFactory queryFactory;


    @Override
    public Page<SlackResponseDto> searchSlack(SlackRequestDto slackRequestDto, Pageable pageable) {

        queryFactory.select(slack)
            .from(slack)
            .where(
                containsMessage(slackRequestDto.contentsRequestDto().message())
                createBeforeTimeAndAfterTime()
            )
    }

    private BooleanExpression containsMessage(String message) {
        return message == null ? null : slack.contents.message.containsIgnoreCase(message);
    }
}
