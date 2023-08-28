package eomgerm.simpleboard.post.controller;

import eomgerm.simpleboard.global.annotation.ExtractPayload;
import eomgerm.simpleboard.global.base.BaseResponse;
import eomgerm.simpleboard.global.base.GlobalErrorCode;
import eomgerm.simpleboard.post.controller.dto.request.CreatePostRequest;
import eomgerm.simpleboard.post.controller.dto.response.CreatePostResponse;
import eomgerm.simpleboard.post.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RequiredArgsConstructor
@RestController
@RequestMapping("api/posts")
@Slf4j
public class PostController {

    private final PostService postService;

    @PostMapping("")
    public ResponseEntity<BaseResponse<CreatePostResponse>> post(@ExtractPayload Long authorId, @RequestBody CreatePostRequest request) {
        log.info("[post] 게시글 작성");
        Long postId = postService.createPost(request.title(), request.content(), authorId);

        return new ResponseEntity<BaseResponse<CreatePostResponse>>(
            new BaseResponse<>(
                GlobalErrorCode.SUCCESS_CREATED,
                CreatePostResponse.builder().postId(postId).build()
            ),
            HttpStatus.CREATED
        );
    }

}
