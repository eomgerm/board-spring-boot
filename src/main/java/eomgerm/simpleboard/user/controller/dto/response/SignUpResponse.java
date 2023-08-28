package eomgerm.simpleboard.user.controller.dto.response;

import lombok.Builder;

@Builder
public record SignUpResponse(
    Long userId
) {

}
