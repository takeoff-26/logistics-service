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
import takeoff.logistics_service.msa.user.application.service.UserService;
import takeoff.logistics_service.msa.user.domain.entity.UserRole;
import takeoff.logistics_service.msa.user.presentation.common.dto.PaginationDto;
import takeoff.logistics_service.msa.user.presentation.dto.request.PatchUserRequestDto;
import takeoff.logistics_service.msa.user.presentation.dto.request.PostSignupRequestDto;
import takeoff.logistics_service.msa.user.presentation.dto.response.*;

import java.time.LocalDateTime;
import java.util.List;

import static com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper.document;
import static com.epages.restdocs.apispec.ResourceDocumentation.resource;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = UserApplication.class)
@AutoConfigureMockMvc(addFilters = false)
@AutoConfigureRestDocs(uriPort = 19000)
@Transactional
@ActiveProfiles("test")
class UserExternalControllerTest {

    @Autowired private MockMvc mockMvc;
    @Autowired private ObjectMapper objectMapper;

    @MockitoBean private UserService userService;

    @Test
    @DisplayName("회원가입 성공")
    void signup_success() throws Exception {
        PostSignupRequestDto request = PostSignupRequestDto.builder()
                .username("newuser")
                .slackEmail("new@user.com")
                .password("Password123!")
                .role(UserRole.COMPANY_MANAGER)
                .companyFeignResponse(null)
                .hubFeignResponse(null)
                .build();

        PostSignupResponseDto response = new PostSignupResponseDto(1L, "newuser", "new@user.com");

        when(userService.signup(any())).thenReturn(response);

        mockMvc.perform(post("/api/v1/users/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(request)))
                .andExpect(status().isOk())
                .andDo(document("user/signup",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        resource(ResourceSnippetParameters.builder()
                                .tag("User-External")
                                .summary("회원가입")
                                .requestFields(
                                        fieldWithPath("username").description("사용자 이름 (4~10자, 소문자/숫자만 가능)"),
                                        fieldWithPath("slackEmail").description("이메일 (형식 검사 포함)"),
                                        fieldWithPath("password").description("비밀번호 (8~20자, 영문/숫자/특수문자 포함)"),
                                        fieldWithPath("role").description("역할 (COMPANY_MANAGER 또는 HUB_MANAGER 등)"),
                                        fieldWithPath("companyFeignResponse").optional().description("회사 정보 (CompanyManager인 경우 필수)"),
                                        fieldWithPath("hubFeignResponse").optional().description("허브 정보 (HubManager인 경우 필수)")
                                )
                                .responseFields(
                                        fieldWithPath("userId").description("생성된 유저 ID"),
                                        fieldWithPath("username").description("사용자명"),
                                        fieldWithPath("slackEmail").description("이메일")
                                )
                                .build())));
    }

    @Test
    @DisplayName("사용자 단건 조회")
    void getUser_success() throws Exception {
        Long userId = 1L;

        GetUserResponseDto response = new GetUserResponseDto(
                userId, "username", "email@slack.com", "HUB_MANAGER"
        );

        when(userService.getUserById(eq(userId), any())).thenReturn(response);

        mockMvc.perform(get("/api/v1/users/{userId}", userId))
                .andExpect(status().isOk())
                .andDo(document("user/get-by-id",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        pathParameters(parameterWithName("userId").description("조회할 사용자 ID")),
                        responseFields(
                                fieldWithPath("userId").description("유저 ID"),
                                fieldWithPath("username").description("사용자명"),
                                fieldWithPath("slackEmail").description("이메일"),
                                fieldWithPath("role").description("역할")
                        )));
    }

    @Test
    @DisplayName("사용자 정보 수정")
    void updateUser_success() throws Exception {
        Long userId = 1L;
        PatchUserRequestDto request = PatchUserRequestDto.builder()
                .username("updatedUser")
                .slackEmail("updated@slack.com")
                .build();

        PatchUserResponseDto response = new PatchUserResponseDto(userId, "updatedUser", "updated@slack.com", "MASTER_ADMIN");

        when(userService.updateUser(eq(userId), any(), any())).thenReturn(response);

        mockMvc.perform(patch("/api/v1/users/{userId}", userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(request)))
                .andExpect(status().isOk())
                .andDo(document("user/update",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        pathParameters(parameterWithName("userId").description("수정할 사용자 ID")),
                        requestFields(
                                fieldWithPath("username").description("수정할 사용자 이름 (필수, 공백 불가)"),
                                fieldWithPath("slackEmail").description("수정할 이메일 (유효한 형식)")
                        ),
                        responseFields(
                                fieldWithPath("userId").description("사용자 ID"),
                                fieldWithPath("username").description("수정된 사용자 이름"),
                                fieldWithPath("slackEmail").description("수정된 이메일"),
                                fieldWithPath("role").description("수정된 역할")
                        )));
    }

    @Test
    @DisplayName("사용자 삭제")
    void deleteUser_success() throws Exception {
        Long userId = 1L;

        DeleteUserResponseDto response = new DeleteUserResponseDto(userId, LocalDateTime.now(), "삭제되었습니다.");

        when(userService.deleteUser(eq(userId), any())).thenReturn(response);

        mockMvc.perform(delete("/api/v1/users/{userId}", userId))
                .andExpect(status().isOk())
                .andDo(document("user/delete",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        pathParameters(parameterWithName("userId").description("삭제할 사용자 ID")),
                        responseFields(
                                fieldWithPath("userId").description("삭제된 유저 ID"),
                                fieldWithPath("deletedAt").description("삭제 시각"),
                                fieldWithPath("message").description("삭제 메시지")
                        )));
    }

    @Test
    @DisplayName("사용자 목록 조회")
    void getUsers_success() throws Exception {
        GetUserListResponseDto response = GetUserListResponseDto.builder()
                .users(List.of(
                        new GetUserListInfoDto(1L, "user1", "u1@slack.com", "HUB_MANAGER", null,"hub-id"),
                        new GetUserListInfoDto(2L, "user2", "u2@slack.com", "COMPANY_MANAGER", "company-id", null)
                ))
                .pagination(new PaginationDto(0, 10, 2, 1))
                .build();

        when(userService.getAllUsers(any(), any())).thenReturn(response);

        mockMvc.perform(get("/api/v1/users")
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andDo(document("user/get-list",
                        resource(ResourceSnippetParameters.builder()
                                .tag("User-External")
                                .summary("사용자 목록 조회")
                                .description("전체 사용자 리스트를 페이징 조회합니다.")
                                .queryParameters(
                                        parameterWithName("page").description("페이지 번호 (0부터 시작)").optional(),
                                        parameterWithName("size").description("페이지 크기 (10, 30, 50만 허용)").optional(),
                                        parameterWithName("username").optional().description("이름 필터 (부분 일치)"),
                                        parameterWithName("email").optional().description("이메일 필터"),
                                        parameterWithName("role").optional().description("역할 필터 (예: MASTER_ADMIN, COMPANY_MANAGER 등)")
                                )
                                .responseFields(
                                        fieldWithPath("users[].userId").description("유저 ID"),
                                        fieldWithPath("users[].username").description("사용자명"),
                                        fieldWithPath("users[].slackEmail").description("이메일"),
                                        fieldWithPath("users[].role").description("역할"),
                                        fieldWithPath("users[].companyId").optional().description("회사 ID (해당되는 경우)"),
                                        fieldWithPath("users[].hubId").optional().description("허브 ID (해당되는 경우)"),
                                        fieldWithPath("pagination.currentPage").description("현재 페이지"),
                                        fieldWithPath("pagination.pageSize").description("페이지 크기"),
                                        fieldWithPath("pagination.totalElements").description("전체 요소 수"),
                                        fieldWithPath("pagination.totalPages").description("전체 페이지 수")
                                )
                                .build()
                        )));
    }
}
