package eomgerm.simpleboard.post.service;

import eomgerm.simpleboard.post.controller.dto.response.PostListResponse;
import eomgerm.simpleboard.post.domain.PostRepository;
import eomgerm.simpleboard.post.domain.SearchType;
import eomgerm.simpleboard.post.domain.SortCondition;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PostListService {

    private final PostRepository postRepository;

    public List<PostListResponse> getPostList(String sortBy, String searchBy, String keyword, Long last) {
        SortCondition sortCondition = SortCondition.findSortConditionByValue(sortBy);
        SearchType searchType = SearchType.findSearchTypeByValue(searchBy);

        List<PostListResponse> postList = new ArrayList<>();
        switch (sortCondition) {
            case RECENT -> postList = postRepository.getPostListOrderByCreatedAt(searchType, keyword, last);
            case LIKE -> postList = postRepository.getPostListOrderByLike(searchType, keyword, last);
        }

        return postList;
    }
}
