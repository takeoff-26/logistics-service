package com.logistics.user.presentation.external;

import com.epages.restdocs.apispec.ResourceSnippetParameters;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import takeoff.logistics_service.msa.user.UserApplication;
import takeoff.logistics_service.msa.user.application.service.DeliveryManagerService;
import takeoff.logistics_service.msa.user.domain.entity.UserRole;
import takeoff.logistics_service.msa.user.domain.vo.DeliveryManagerType;
import takeoff.logistics_service.msa.user.presentation.common.dto.PaginationDto;
import takeoff.logistics_service.msa.user.presentation.dto.request.PatchDeliveryManagerRequestDto;
import takeoff.logistics_service.msa.user.presentation.dto.request.PostDeliveryManagerRequestDto;
import takeoff.logistics_service.msa.user.presentation.dto.response.*;

import java.util.List;
import java.util.UUID;

import static com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper.document;
import static com.epages.restdocs.apispec.ResourceDocumentation.resource;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = UserApplication.class)
@AutoConfigureMockMvc(addFilters = false)
@AutoConfigureRestDocs(uriPort = 19000)
@Transactional
@ActiveProfiles("test")
class DeliveryManagerExternalControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private DeliveryManagerService deliveryManagerService;

    @Autowired
    private ObjectMapper objectMapper;

    private final UUID testHubId = UUID.nameUUIDFromBytes("hub-for-test".getBytes());

    @Test
    @DisplayName("업체 배송 관리자 생성 성공")
    void createCompanyDeliveryManager() throws Exception {
        // given
        PostDeliveryManagerRequestDto request = PostDeliveryManagerRequestDto.builder()
                .username("company_dm")
                .slackEmail("company@dm.com")
                .password("password123!")
                .role(UserRole.COMPANY_DELIVERY_MANAGER)
                .deliveryManagerType(DeliveryManagerType.COMPANY_DELIVERY_MANAGER)
                .identifier(testHubId.toString())
                .deliverySequence(0)
                .build();

        when(deliveryManagerService.createDeliveryManager(any(), any()))
                .thenReturn(PostDeliveryManagerResponseDto.builder()
                        .userId(1L)
                        .username("company_dm")
                        .slackEmail("company@dm.com")
                        .deliveryManagerType(DeliveryManagerType.COMPANY_DELIVERY_MANAGER)
                        .identifier(testHubId.toString())
                        .deliverySequence(0)
                        .build());


        mockMvc.perform(post("/api/v1/delivery-managers/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(request)))
                .andExpect(status().isOk())
                .andDo(document("delivery-manager/create-company",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        resource(ResourceSnippetParameters.builder()
                                .tag("DeliveryManager-External")
                                .summary("업체 배송 관리자 생성")
                                .description("배송 담당자 중 업체 담당자를 생성합니다.")
                                .requestFields(
                                        fieldWithPath("username").description("사용자명"),
                                        fieldWithPath("slackEmail").description("슬랙 이메일"),
                                        fieldWithPath("password").description("비밀번호"),
                                        fieldWithPath("role").description("역할 (COMPANY_DELIVERY_MANAGER)"),
                                        fieldWithPath("deliveryManagerType").description("배송 관리자 타입"),
                                        fieldWithPath("identifier").description("허브 ID"),
                                        fieldWithPath("deliverySequence").description("배송 순서 (입력은 무시됨)")
                                )
                                .responseFields(
                                        fieldWithPath("userId").description("생성된 유저 ID"),
                                        fieldWithPath("username").description("사용자명"),
                                        fieldWithPath("slackEmail").description("이메일"),
                                        fieldWithPath("deliveryManagerType").description("배송 관리자 타입"),
                                        fieldWithPath("identifier").description("허브 ID"),
                                        fieldWithPath("deliverySequence").description("배송 순서")
                                )
                                .build()
                        )
                ));
    }

    @Test
    @DisplayName("허브 배송 관리자 생성 성공")
    void createHubDeliveryManager() throws Exception {
        // given
        PostDeliveryManagerRequestDto request = PostDeliveryManagerRequestDto.builder()
                .username("hub_dm")
                .slackEmail("hub@dm.com")
                .password("password123!")
                .role(UserRole.HUB_DELIVERY_MANAGER)
                .deliveryManagerType(DeliveryManagerType.HUB_DELIVERY_MANAGER)
                .identifier(testHubId.toString())
                .deliverySequence(0)
                .build();

        when(deliveryManagerService.createDeliveryManager(any(), any()))
                .thenReturn(PostDeliveryManagerResponseDto.builder()
                        .userId(2L)
                        .username("hub_dm")
                        .slackEmail("hub@dm.com")
                        .deliveryManagerType(DeliveryManagerType.HUB_DELIVERY_MANAGER)
                        .identifier(testHubId.toString())
                        .deliverySequence(0)
                        .build()
                );


        // when & then
        mockMvc.perform(post("/api/v1/delivery-managers/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(request)))
                .andExpect(status().isOk())
                .andDo(document("delivery-manager/create-hub",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        resource(ResourceSnippetParameters.builder()
                                .tag("DeliveryManager-External")
                                .summary("허브 배송 관리자 생성")
                                .description("배송 담당자 중 허브 담당자를 생성합니다.")
                                .requestFields(
                                        fieldWithPath("username").description("사용자명"),
                                        fieldWithPath("slackEmail").description("슬랙 이메일"),
                                        fieldWithPath("password").description("비밀번호"),
                                        fieldWithPath("role").description("역할 (HUB_DELIVERY_MANAGER)"),
                                        fieldWithPath("deliveryManagerType").description("배송 관리자 타입"),
                                        fieldWithPath("identifier").description("허브 ID"),
                                        fieldWithPath("deliverySequence").description("배송 순서 (입력은 무시됨)")
                                )
                                .responseFields(
                                        fieldWithPath("userId").description("생성된 유저 ID"),
                                        fieldWithPath("username").description("사용자명"),
                                        fieldWithPath("slackEmail").description("이메일"),
                                        fieldWithPath("deliveryManagerType").description("배송 관리자 타입"),
                                        fieldWithPath("identifier").description("허브 ID"),
                                        fieldWithPath("deliverySequence").description("배송 순서")
                                )
                                .build()
                        )
                ));
    }

    @Test
    @DisplayName("배송 관리자 단건 조회 성공")
    void getDeliveryManagerById_success() throws Exception {
        Long id = 1L;

        GetDeliveryManagerResponseDto response = new GetDeliveryManagerResponseDto(
                id, "manager1", "test@slack.com", DeliveryManagerType.COMPANY_DELIVERY_MANAGER, "hub-id", 0
        );

        when(deliveryManagerService.getDeliveryManagerById(eq(id), any()))
                .thenReturn(response);

        mockMvc.perform(get("/api/v1/delivery-managers/{id}", id))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("delivery-manager/get-by-id",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        pathParameters(
                                parameterWithName("id").description("조회할 배송 관리자 ID")
                        ),
                        responseFields(
                                fieldWithPath("userId").description("배송 관리자 ID"),
                                fieldWithPath("username").description("이름"),
                                fieldWithPath("slackEmail").description("슬랙 이메일"),
                                fieldWithPath("deliveryManagerType").description("배송 관리자 타입"),
                                fieldWithPath("identifier").description("허브 또는 회사 ID"),
                                fieldWithPath("deliverySequence").description("배송 순번")
                        )
                ));
    }

    @Test
    @DisplayName("배송 관리자 목록 조회 성공")
    void getDeliveryManagers_success() throws Exception {
        GetDeliveryManagerListResponseDto response = GetDeliveryManagerListResponseDto.builder()
                .deliveryManagers(List.of(
                        new GetDeliveryManagerListInfoDto(1L, "manager1", "m1@slack.com", "HUB_DELIVERY_MANAGER", "hub1", 0),
                        new GetDeliveryManagerListInfoDto(2L, "manager2", "m2@slack.com", "COMPANY_DELIVERY_MANAGER", "hub2", 0),
                        new GetDeliveryManagerListInfoDto(3L, "manager3", "m3@slack.com", "HUB_DELIVERY_MANAGER", "hub3", 1),
                        new GetDeliveryManagerListInfoDto(4L, "manager4", "m4@slack.com", "COMPANY_DELIVERY_MANAGER", "hub4", 1),
                        new GetDeliveryManagerListInfoDto(5L, "manager5", "m5@slack.com", "HUB_DELIVERY_MANAGER", "hub5", 2),
                        new GetDeliveryManagerListInfoDto(6L, "manager6", "m6@slack.com", "COMPANY_DELIVERY_MANAGER", "hub6", 2)
                ))
                .pagination(new PaginationDto(0, 10, 2, 1))
                .build();

        when(deliveryManagerService.getAllDeliveryManagers(any(), any()))
                .thenReturn(response);

        mockMvc.perform(get("/api/v1/delivery-managers")
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("delivery-manager/get-list",
                        resource(ResourceSnippetParameters.builder()
                                .tag("DeliveryManager-External")
                                .summary("배송 관리자 목록 조회")
                                .description("전체 배송 관리자 목록을 페이징으로 조회합니다.")
                                .queryParameters(
                                        parameterWithName("page").description("페이지 번호"),
                                        parameterWithName("size").description("페이지 크기"),
                                        parameterWithName("hubId").optional().description("허브 ID 필터링"),
                                        parameterWithName("deliveryManagerType").optional().description("배송 관리자 타입 필터링")
                                )
                                .responseFields(
                                        fieldWithPath("deliveryManagers[].deliveryManagerId").description("배송 관리자 ID"),
                                        fieldWithPath("deliveryManagers[].username").description("사용자 이름"),
                                        fieldWithPath("deliveryManagers[].slackEmail").description("슬랙 이메일"),
                                        fieldWithPath("deliveryManagers[].deliveryManagerType").description("배송 관리자 타입"),
                                        fieldWithPath("deliveryManagers[].identifier").description("허브 또는 회사 ID"),
                                        fieldWithPath("deliveryManagers[].sequenceNumber").description("배송 순번"),
                                        fieldWithPath("pagination.currentPage").description("현재 페이지"),
                                        fieldWithPath("pagination.pageSize").description("페이지 크기"),
                                        fieldWithPath("pagination.totalElements").description("전체 요소 수"),
                                        fieldWithPath("pagination.totalPages").description("전체 페이지 수")
                                )
                                .build()
                        )
                ));
    }


    @Test
    @DisplayName("배송 관리자 수정 성공")
    void updateDeliveryManager_success() throws Exception {
        Long id = 1L;
        PatchDeliveryManagerRequestDto request = PatchDeliveryManagerRequestDto.builder()
                .slackEmail("update@slack.com")
                .deliverySequence(1)
                .hubId("hub-updated")
                .build();

        PatchDeliveryManagerResponseDto response = PatchDeliveryManagerResponseDto.builder()
                .userId(id)
                .username("manager1")
                .slackEmail("update@slack.com")
                .deliveryManagerType(DeliveryManagerType.HUB_DELIVERY_MANAGER)
                .identifier("hub-updated")
                .deliverySequence(1)
                .build();

        when(deliveryManagerService.updateDeliveryManager(eq(id), any(), any()))
                .thenReturn(response);

        mockMvc.perform(patch("/api/v1/delivery-managers/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(request)))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("delivery-manager/update",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        pathParameters(
                                parameterWithName("id").description("수정할 배송 관리자 ID")
                        ),
                        requestFields(
                                fieldWithPath("slackEmail").description("수정할 슬랙 이메일"),
                                fieldWithPath("deliverySequence").description("배송 순서"),
                                fieldWithPath("hubId").optional().description("허브 ID"),
                                fieldWithPath("companyId").optional().description("회사 ID")
                        ),
                        responseFields(
                                fieldWithPath("userId").description("수정된 사용자 ID"),
                                fieldWithPath("username").description("사용자 이름"),
                                fieldWithPath("slackEmail").description("슬랙 이메일"),
                                fieldWithPath("deliveryManagerType").description("배송 관리자 타입"),
                                fieldWithPath("identifier").description("허브 또는 회사 ID"),
                                fieldWithPath("deliverySequence").description("배송 순서")
                        )
                ));
    }

    @Test
    @DisplayName("배송 관리자 삭제 성공")
    void deleteDeliveryManager_success() throws Exception {
        Long id = 1L;

        when(deliveryManagerService.deleteDeliveryManager(eq(id), any()))
                .thenReturn(DeleteDeliveryManagerResponseDto.from(id));

        mockMvc.perform(delete("/api/v1/delivery-managers/{id}", id))
                .andExpect(status().isOk())
                .andDo(document("delivery-manager/delete",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        pathParameters(
                                parameterWithName("id").description("삭제할 배송 관리자 ID")
                        ),
                        responseFields(
                                fieldWithPath("userId").description("삭제된 사용자 ID"),
                                fieldWithPath("deletedAt").description("삭제된 시각"),
                                fieldWithPath("message").description("삭제 성공 메시지")
                        )
                ));
    }

}
