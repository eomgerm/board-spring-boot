package eomgerm.simpleboard.auth.controller;

import static eomgerm.simpleboard.fixture.TokenFixture.ACCESS_TOKEN;
import static eomgerm.simpleboard.fixture.TokenFixture.REFRESH_TOKEN;
import static eomgerm.simpleboard.fixture.UserFixture.ALEX;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.beneathPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import eomgerm.simpleboard.auth.controller.dto.request.LoginRequest;
import eomgerm.simpleboard.auth.controller.dto.response.LoginResponse;
import eomgerm.simpleboard.auth.exception.AuthErrorCode;
import eomgerm.simpleboard.common.ControllerTest;
import eomgerm.simpleboard.global.base.BaseException;
import eomgerm.simpleboard.global.base.GlobalErrorCode;
import eomgerm.simpleboard.user.exception.UserErrorCode;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

@DisplayName("AuthController 테스트")
@WebMvcTest(AuthController.class)
class AuthControllerTest extends ControllerTest {

    @Nested
    @DisplayName("로그인 API [POST /api/auth/login]")
    class Login {

        private static final String BASE_URL = "/api/auth/login";

        @Test
        @DisplayName("이메일이 일치하지 않으면 로그인에 실패")
        void throwExceptionByWrongEmail() throws Exception {
            //given
            doThrow(BaseException.of(UserErrorCode.USER_NOT_FOUND))
                .when(authService)
                .login(any(), any());

            //when
            final LoginRequest request = createLoginRequest();
            MockHttpServletRequestBuilder requestBuilder = RestDocumentationRequestBuilders
                .post(BASE_URL)
                .contentType(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request));

            //then
            final UserErrorCode expectedError = UserErrorCode.USER_NOT_FOUND;
            mockMvc.perform(requestBuilder)
                   .andExpectAll(
                       status().isNotFound(),
                       jsonPath("$.status").exists(),
                       jsonPath("$.status").value(expectedError.getStatus().value()),
                       jsonPath("$.errorCode").exists(),
                       jsonPath("$.errorCode").value(expectedError.getErrorCode()),
                       jsonPath("$.message").exists(),
                       jsonPath("$.message").value(expectedError.getMessage())
                   )
                   .andDo(
                       document(
                           "Auth/Login/Failure/Case1",
                           preprocessRequest(prettyPrint()),
                           preprocessResponse(prettyPrint()),
                           requestFields(
                               fieldWithPath("email").description("이메일"),
                               fieldWithPath("password").description("비밀번호")
                           ),
                           responseFields(
                               fieldWithPath("status").description("HTTP 상태 코드"),
                               fieldWithPath("errorCode").description("예외 코드"),
                               fieldWithPath("message").description("예외 메시지")
                           )
                       )
                   );
        }

        @Test
        @DisplayName("비밀번호가 올바르지 않으면 로그인에 실패한다")
        void throwsExceptionByWrongPassword() throws Exception {
            //given
            doThrow(BaseException.of(AuthErrorCode.INVALID_LOGIN_REQUEST))
                .when(authService)
                .login(any(), any());

            //when
            final LoginRequest request = createLoginRequest();
            MockHttpServletRequestBuilder requestBuilder = RestDocumentationRequestBuilders
                .post(BASE_URL)
                .contentType(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request));

            //then
            final AuthErrorCode expectedError = AuthErrorCode.INVALID_LOGIN_REQUEST;
            mockMvc.perform(requestBuilder)
                   .andExpectAll(
                       status().isUnauthorized(),
                       jsonPath("$.status").exists(),
                       jsonPath("$.status").value(expectedError.getStatus().value()),
                       jsonPath("$.errorCode").exists(),
                       jsonPath("$.errorCode").value(expectedError.getErrorCode()),
                       jsonPath("$.message").exists(),
                       jsonPath("$.message").value(expectedError.getMessage())
                   )
                   .andDo(
                       document(
                           "Auth/Login/Failure/Case2",
                           preprocessRequest(prettyPrint()),
                           preprocessResponse(prettyPrint()),
                           requestFields(
                               fieldWithPath("email").description("이메일"),
                               fieldWithPath("password").description("비밀번호")
                           ),
                           responseFields(
                               fieldWithPath("status").description("HTTP 상태 코드"),
                               fieldWithPath("errorCode").description("예외 코드"),
                               fieldWithPath("message").description("예외 메시지")
                           )
                       )
                   );
        }

        @Test
        @DisplayName("로그인에 성공한다")
        void success() throws Exception {
            //given
            final LoginResponse loginResponse = createLoginResponse();
            doReturn(loginResponse).when(authService).login(any(), any());

            //when
            final LoginRequest request = createLoginRequest();
            MockHttpServletRequestBuilder requestBuilder = RestDocumentationRequestBuilders
                .post(BASE_URL)
                .contentType(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request));

            //then
            final GlobalErrorCode expectedStatus = GlobalErrorCode.SUCCESS_OK;
            mockMvc.perform(requestBuilder)
                   .andExpectAll(
                       status().isOk(),
                       jsonPath("$.status").exists(),
                       jsonPath("$.status").value(expectedStatus.getStatus().value()),
                       jsonPath("$.errorCode").exists(),
                       jsonPath("$.errorCode").value(expectedStatus.getErrorCode()),
                       jsonPath("$.message").exists(),
                       jsonPath("$.message").value(expectedStatus.getMessage()),
                       jsonPath("$.result.accessToken").exists(),
                       jsonPath("$.result.accessToken").value(ACCESS_TOKEN),
                       jsonPath("$.result.refreshToken").exists(),
                       jsonPath("$.result.refreshToken").value(REFRESH_TOKEN)
                   )
                   .andDo(
                       document(
                           "Auth/Login/Success",
                           preprocessRequest(prettyPrint()),
                           preprocessResponse(prettyPrint()),
                           requestFields(
                               fieldWithPath("email").description("이메일"),
                               fieldWithPath("password").description("비밀번호")
                           ),
                           responseFields(
                               beneathPath("result"),
                               fieldWithPath("accessToken").description("JWT AccessToken"),
                               fieldWithPath("refreshToken").description("JWT Refresh Token")
                           )
                       )
                   );
        }
    }

    private LoginRequest createLoginRequest() {
        return new LoginRequest(ALEX.getEmail(), ALEX.getPassword());
    }

    private LoginResponse createLoginResponse() {
        return new LoginResponse(ACCESS_TOKEN, REFRESH_TOKEN);
    }
}