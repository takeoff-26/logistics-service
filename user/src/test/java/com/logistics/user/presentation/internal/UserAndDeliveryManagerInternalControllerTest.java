package com.logistics.user.presentation.internal;

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
import takeoff.logistics_service.msa.user.application.service.UserService;
import takeoff.logistics_service.msa.user.presentation.dto.request.*;
import takeoff.logistics_service.msa.user.presentation.dto.response.*;

import java.util.List;
import java.util.UUID;

import static com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper.document;
import static com.epages.restdocs.apispec.ResourceDocumentation.resource;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = UserApplication.class)
@AutoConfigureMockMvc(addFilters = false)
@AutoConfigureRestDocs(uriPort = 19000)
@Transactional
@ActiveProfiles("test")
class UserAndDeliveryManagerInternalControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private UserService userService;

    @MockitoBean
    private DeliveryManagerService deliveryManagerService;

    @Test
    @DisplayName("회사 관리자 ID로 사용자 목록 조회")
    void getUsersByCompanyManagerId() throws Exception {
        when(userService.getUsersByCompanyManagerId(eq(1L), any())).thenReturn(
                List.of(new GetManagerListInfoDto(1L, "user1", "email1@slack.com", "COMPANY_MANAGER", "company-id", null))
        );

        mockMvc.perform(get("/api/v1/app/users/managers/company/users")
                        .param("managerId", "1"))
                .andExpect(status().isOk())
                .andDo(document("user-internal/get-by-company-manager-id",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        queryParameters(
                                parameterWithName("managerId").description("회사 관리자 ID")
                        ),
                        responseFields(
                                fieldWithPath("users[].userId").description("사용자 ID"),
                                fieldWithPath("users[].username").description("사용자명"),
                                fieldWithPath("users[].slackEmail").description("이메일"),
                                fieldWithPath("users[].role").description("역할"),
                                fieldWithPath("users[].companyId").optional().description("회사 ID"),
                                fieldWithPath("users[].hubId").optional().description("허브 ID")
                        )
                ));
    }

    @Test
    @DisplayName("허브 관리자 ID로 사용자 목록 조회")
    void getUsersByHubManagerId() throws Exception {
        when(userService.getUsersByHubManagerId(eq(1L), any())).thenReturn(
                List.of(new GetManagerListInfoDto(2L, "user2", "email1@slack.com", "HUB_MANAGER", null,"hub-id"))
        );

        mockMvc.perform(get("/api/v1/app/users/managers/hub/users")
                        .param("managerId", "1"))
                .andExpect(status().isOk())
                .andDo(document("user-internal/get-by-hub-manager-id",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        queryParameters(
                                parameterWithName("managerId").description("허브 관리자 ID")
                        ),
                        responseFields(
                                fieldWithPath("users[].userId").description("사용자 ID"),
                                fieldWithPath("users[].username").description("사용자명"),
                                fieldWithPath("users[].slackEmail").description("이메일"),
                                fieldWithPath("users[].role").description("역할"),
                                fieldWithPath("users[].companyId").optional().description("회사 ID"),
                                fieldWithPath("users[].hubId").optional().description("허브 ID")
                        )
                ));
    }

    @Test
    @DisplayName("유저 검증 성공")
    void validateUser_success() throws Exception {
        UserValidationRequestDto request = new UserValidationRequestDto("username", "Password123!");
        UserValidationResponseDto response = new UserValidationResponseDto("1", "username", "COMPANY_MANAGER");

        when(userService.validateUser(any())).thenReturn(response);

        mockMvc.perform(post("/api/v1/app/users/validate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(request)))
                .andExpect(status().isOk())
                .andDo(document("user-internal/validate",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestFields(
                                fieldWithPath("username").description("사용자 이름"),
                                fieldWithPath("password").description("비밀번호")
                        ),
                        responseFields(
                                fieldWithPath("userId").description("유저 ID"),
                                fieldWithPath("username").description("사용자 이름"),
                                fieldWithPath("role").description("역할")
                        )
                ));
    }

    @Test
    @DisplayName("허브 ID로 업체 배송 관리자 조회")
    void getCompanyDeliveryManagersByHubId() throws Exception {
        UUID hubId = UUID.randomUUID();

        when(deliveryManagerService.getCompanyDeliveryManagersByHubId(eq(hubId), any())).thenReturn(
                List.of(new GetDeliveryManagerListInfoDto(1L, "dm1", "d1@slack.com", "COMPANY_DELIVERY_MANAGER", hubId.toString(), 0))
        );

        mockMvc.perform(get("/api/v1/app/delivery-managers/company")
                        .param("hubId", hubId.toString()))
                .andExpect(status().isOk())
                .andDo(document("delivery-manager-internal/get-company-by-hub-id",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        queryParameters(parameterWithName("hubId").description("허브 ID")),
                        responseFields(
                                fieldWithPath("deliveryManagers[].deliveryManagerId").description("배송 관리자 ID"),
                                fieldWithPath("deliveryManagers[].username").description("이름"),
                                fieldWithPath("deliveryManagers[].slackEmail").description("이메일"),
                                fieldWithPath("deliveryManagers[].deliveryManagerType").description("배송 관리자 타입"),
                                fieldWithPath("deliveryManagers[].identifier").description("허브 또는 회사 ID"),
                                fieldWithPath("deliveryManagers[].sequenceNumber").description("배송 순번")
                        )
                ));
    }

    @Test
    @DisplayName("허브 배송 관리자 전체 조회")
    void getAllHubDeliveryManagers() throws Exception {
        when(deliveryManagerService.getAllHubDeliveryManagers(any())).thenReturn(
                List.of(new GetDeliveryManagerListInfoDto(1L, "dm1", "d1@slack.com", "HUB_DELIVERY_MANAGER", "hub-id", 0))
        );

        mockMvc.perform(get("/api/v1/app/delivery-managers/hub"))
                .andExpect(status().isOk())
                .andDo(document("delivery-manager-internal/get-all-hub",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        responseFields(
                                fieldWithPath("deliveryManagers[].deliveryManagerId").description("배송 관리자 ID"),
                                fieldWithPath("deliveryManagers[].username").description("이름"),
                                fieldWithPath("deliveryManagers[].slackEmail").description("이메일"),
                                fieldWithPath("deliveryManagers[].deliveryManagerType").description("배송 관리자 타입"),
                                fieldWithPath("deliveryManagers[].identifier").description("허브 또는 회사 ID"),
                                fieldWithPath("deliveryManagers[].sequenceNumber").description("배송 순번")
                        )
                ));
    }
}
