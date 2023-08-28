package eomgerm.simpleboard.post.controller.dto.response;

import lombok.Builder;

@Builder
public record CreatePostResponse(
    Long postId
) {

}
