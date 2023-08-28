package eomgerm.simpleboard.user.controller;

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

import eomgerm.simpleboard.common.ControllerTest;
import eomgerm.simpleboard.global.base.BaseException;
import eomgerm.simpleboard.global.base.GlobalErrorCode;
import eomgerm.simpleboard.user.controller.dto.request.SignUpRequest;
import eomgerm.simpleboard.user.exception.UserErrorCode;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

@DisplayName("UserController 테스트")
@WebMvcTest(UserController.class)
class UserControllerTest extends ControllerTest {

    @Nested
    @DisplayName("회원 가입 API [/api/user/sign-up]")
    class SignUp {

        private static final String BASE_URL = "/api/user/sign-up";
        private static final Long USER_ID = 1L;

        @Test
        @DisplayName("이메일이 중복되면 회원 가입 실패")
        void throwExceptionByDuplicatedEmail() throws Exception {
            //given
            doThrow(BaseException.of(UserErrorCode.DUPLICATE_EMAIL))
                .when(userService)
                .saveUser(any());

            //when
            final SignUpRequest request = createSignUpRequest();
            MockHttpServletRequestBuilder requestBuilder = RestDocumentationRequestBuilders
                .post(BASE_URL)
                .contentType(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request));

            //then
            final UserErrorCode expectedError = UserErrorCode.DUPLICATE_EMAIL;
            mockMvc.perform(requestBuilder)
                   .andExpectAll(
                       status().isConflict(),
                       jsonPath("$.status").exists(),
                       jsonPath("$.status").value(expectedError.getStatus().value()),
                       jsonPath("$.errorCode").exists(),
                       jsonPath("$.errorCode").value(expectedError.getErrorCode()),
                       jsonPath("$.message").exists(),
                       jsonPath("$.message").value(expectedError.getMessage())
                   )
                   .andDo(
                       document(
                           "User/SignUp/Failure/Case1",
                           preprocessRequest(prettyPrint()),
                           preprocessResponse(prettyPrint()),
                           requestFields(
                               fieldWithPath("email").description("이메일"),
                               fieldWithPath("password").description("비밀번호"),
                               fieldWithPath("username").description("닉네임")
                           ),
                           responseFields(
                               fieldWithPath("status").description("HTTP 상태 코드"),
                               fieldWithPath("errorCode").description("에러 코드"),
                               fieldWithPath("message").description("에러 메시지")
                           )
                       )
                   );
        }

        @Test
        @DisplayName("회원 가입 성공")
        void success() throws Exception {
            //given
            doReturn(USER_ID)
                .when(userService)
                .saveUser(any());

            //when
            final SignUpRequest request = createSignUpRequest();
            MockHttpServletRequestBuilder requestBuilder = RestDocumentationRequestBuilders
                .post(BASE_URL)
                .contentType(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request));

            //then
            final GlobalErrorCode expectedStatus = GlobalErrorCode.SUCCESS_CREATED;
            mockMvc.perform(requestBuilder)
                   .andExpectAll(
                       status().isCreated(),
                       jsonPath("$.status").exists(),
                       jsonPath("$.status").value(expectedStatus.getStatus().value()),
                       jsonPath("$.errorCode").exists(),
                       jsonPath("$.errorCode").value(expectedStatus.getErrorCode()),
                       jsonPath("$.message").exists(),
                       jsonPath("$.message").value(expectedStatus.getMessage()),
                       jsonPath("$.result.userId").exists(),
                       jsonPath("$.result.userId").value(USER_ID)
                   )
                   .andDo(document(
                           "User/SignUp/Success",
                           preprocessRequest(prettyPrint()),
                           preprocessResponse(prettyPrint()),
                           requestFields(
                               fieldWithPath("email").description("이메일"),
                               fieldWithPath("password").description("비밀번호"),
                               fieldWithPath("username").description("닉네임")
                           ),
                           responseFields(
                               beneathPath("result"),
                               fieldWithPath("userId").description("생성된 사용자의 아이디")
                           )
                       )
                   );
        }
    }

    private SignUpRequest createSignUpRequest() {
        return new SignUpRequest(ALEX.getEmail(), ALEX.getPassword(), ALEX.getUsername());
    }
}