package takeoff.logistics_service.msa.slack.application.service;

import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import takeoff.logistics_service.msa.slack.presentation.dto.request.SlackRequestDto;
import takeoff.logistics_service.msa.slack.presentation.dto.response.SlackResponseDto;

/**
 * @author : hanjihoon
 * @Date : 2025. 03. 13.
 */
public interface SlackService {

    SlackResponseDto createSlackMessage(SlackRequestDto requestDto);

    SlackResponseDto findBySlackId(UUID slackId);

    SlackResponseDto updateBySlack(UUID slackId,SlackRequestDto requestDto);

//      Auditing 설정시 추가 개발 예정
//    void deleteBySlack(UUID slackId);

    Page<SlackResponseDto> searchSlack(SlackRequestDto slackRequestDto, Pageable pageable);

}
