package eomgerm.simpleboard.post.controller;

import eomgerm.simpleboard.global.base.BaseResponse;
import eomgerm.simpleboard.global.base.GlobalErrorCode;
import eomgerm.simpleboard.post.controller.dto.response.PostListResponse;
import eomgerm.simpleboard.post.service.PostListService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/posts")
public class PostListController {

    private final PostListService postListService;

    @GetMapping("")
    public ResponseEntity<BaseResponse<List<PostListResponse>>> getPostList(
        @RequestParam(value = "sortBy", required = false, defaultValue = "recent") String sortBy,
        @RequestParam(value = "searchBy", required = false, defaultValue = "title") String searchBy,
        @RequestParam(value = "keyword", required = false, defaultValue = "") String keyword,
        @RequestParam(value = "last", required = false, defaultValue = "-1") Long last) {

        return new ResponseEntity<>(
            new BaseResponse<>(
                GlobalErrorCode.SUCCESS_OK,
                postListService.getPostList(sortBy, searchBy, keyword, last)
            ),
            HttpStatus.OK
        );
    }
}
