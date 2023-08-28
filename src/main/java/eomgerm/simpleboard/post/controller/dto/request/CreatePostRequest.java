package eomgerm.simpleboard.post.controller.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreatePostRequest(
    @NotBlank
    @Size(min = 5, message = "제목은 5자 이상이어야 합니다.")
    @Size(min = 50, message = "제목은 50자 이하여야 합니다.")
    String title,

    String content
) {

}
