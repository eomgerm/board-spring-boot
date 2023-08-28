package eomgerm.simpleboard.post.controller.dto.response;

import com.querydsl.core.annotations.QueryProjection;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PostListResponse {

    private final Author author;
    private final String title;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;
    private final Long likeCount;

    @QueryProjection
    public PostListResponse(Author author, String title, LocalDateTime createdAt, LocalDateTime updatedAt, Long likeCount) {
        this.author = author;
        this.title = title;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.likeCount = likeCount;
    }
}
