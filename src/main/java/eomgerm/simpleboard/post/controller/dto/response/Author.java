package eomgerm.simpleboard.post.controller.dto.response;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Author {

    private final Long userId;
    private final String profileImage;
    private final String username;

    @QueryProjection
    public Author(Long userId, String profileImage, String username) {
        this.userId = userId;
        this.profileImage = profileImage;
        this.username = username;
    }
}
