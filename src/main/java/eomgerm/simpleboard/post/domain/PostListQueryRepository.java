package eomgerm.simpleboard.post.domain;

import eomgerm.simpleboard.post.controller.dto.response.PostListResponse;
import java.util.List;

public interface PostListQueryRepository {

    public List<PostListResponse> getPostListOrderByCreatedAt(SearchType searchType, String keyword, Long last);

    public List<PostListResponse> getPostListOrderByLike(SearchType searchType, String keyword, Long last);
}
