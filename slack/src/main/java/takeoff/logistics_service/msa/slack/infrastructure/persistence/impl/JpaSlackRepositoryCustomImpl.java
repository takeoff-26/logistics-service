package takeoff.logistics_service.msa.slack.infrastructure.persistence.impl;

import static takeoff.logistics_service.msa.slack.model.entity.QSlack.slack;

import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import takeoff.logistics_service.msa.slack.infrastructure.persistence.JpaSlackRepositoryCustom;
import takeoff.logistics_service.msa.slack.model.entity.QSlack;
import takeoff.logistics_service.msa.slack.model.entity.Slack;
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

        List<Slack> fetch = queryFactory.select(slack)
            .from(slack)
            .where(
                containsMessage(slackRequestDto.contentsRequestDto().message())
            )
            //Auditor 생성시 변경해야함.
            .orderBy(slack.contents.sent_At.asc())
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .fetch();

        Long totalCount = queryFactory.select(slack.count())
            .from(slack)
            .where(
                containsMessage(slackRequestDto.contentsRequestDto().message())
            )
            .fetchOne();

        if (totalCount == null) totalCount = 0L;

        List<SlackResponseDto> responseDtoList = fetch.stream()
            .map(SlackResponseDto::from)
            .toList();


        return new PageImpl<>(responseDtoList, pageable, totalCount);
    }

    private BooleanExpression containsMessage(String message) {
        return message == null ? null : slack.contents.message.containsIgnoreCase(message);
    }

    //Auditor 구성시 정렬 추가
//    private List<OrderSpecifier<?>> dynamicOrder(Pageable pageable) {
//        List<OrderSpecifier<?>> orderSpecifierList = new ArrayList<>();
//
//        if (pageable.getSort() != null) {
//            for (Sort.Order sortOrder : pageable.getSort()) {
//                Order direction = sortOrder.isAscending() ? Order.ASC : Order.DESC;
//
//                switch (sortOrder.getProperty()) {
//                    case "createdAt":
//                        orderSpecifierList.add(new OrderSpecifier<>(direction, slack.createdAt));
//                        break;
//                    case "updatedAt":
//                        orderSpecifierList.add(new OrderSpecifier<>(direction, slack.updatedAt));
//                        break;
//                    default:
//                        throw new IllegalArgumentException(
//                            "잘못된 정렬 필드입니다. : " + sortOrder.getProperty());
//                }
//            }
//        } else {
//            orderSpecifierList.add(new OrderSpecifier<>(Order.ASC, slack.createdAt));
//        }
//        return orderSpecifierList;
//
//    }
}
