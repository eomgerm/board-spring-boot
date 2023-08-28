package eomgerm.simpleboard.post.controller;

import static eomgerm.simpleboard.fixture.PostFixture.DOG;
import static eomgerm.simpleboard.fixture.TokenFixture.ACCESS_TOKEN;
import static eomgerm.simpleboard.fixture.TokenFixture.BEARER;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
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
import eomgerm.simpleboard.global.base.GlobalErrorCode;
import eomgerm.simpleboard.post.controller.dto.request.CreatePostRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;


@DisplayName("PostController 테스트")
@WebMvcTest(PostController.class)
class PostControllerTest extends ControllerTest {

    @Nested
    @DisplayName("게시물 작성 API [POST /api/posts]")
    class post {

        private static final String BASE_URL = "/api/post";
        private static final Long POST_ID = 1L;

        @Test
        @DisplayName("게시물 작성 성공")
        void success() throws Exception {
            //when
            doReturn(POST_ID)
                .when(postService)
                .createPost(any(), any(), any());

            //given
            final CreatePostRequest request = createPostRequest();
            MockHttpServletRequestBuilder requestBuilder = RestDocumentationRequestBuilders
                .post(BASE_URL)
                .header(AUTHORIZATION, BEARER + " " + ACCESS_TOKEN)
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
                       jsonPath("$.result.postId").exists(),
                       jsonPath("$.result.postId").value(POST_ID)
                   )
                   .andDo(
                       document(
                           "Post/Post/Success",
                           preprocessRequest(prettyPrint()),
                           preprocessResponse(prettyPrint()),
                           requestHeaders(
                               headerWithName(AUTHORIZATION).description("JWT Access Token")
                           ),
                           requestFields(
                               fieldWithPath("title").description("글 제목"),
                               fieldWithPath("content").description("글 내용")
                           ),
                           responseFields(
                               beneathPath("result"),
                               fieldWithPath(" postId").description("작성된 글의 아이디")
                           )
                       )
                   );
        }

    }

    private CreatePostRequest createPostRequest() {
        return new CreatePostRequest(DOG.getTitle(), DOG.getContent());
    }

}