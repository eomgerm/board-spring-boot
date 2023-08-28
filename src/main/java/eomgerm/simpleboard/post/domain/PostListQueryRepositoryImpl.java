package eomgerm.simpleboard.post.domain;

import static eomgerm.simpleboard.post.domain.QPost.post;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import eomgerm.simpleboard.post.controller.dto.response.PostListResponse;
import eomgerm.simpleboard.post.controller.dto.response.QAuthor;
import eomgerm.simpleboard.post.controller.dto.response.QPostListResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PostListQueryRepositoryImpl implements PostListQueryRepository {

    private final JPAQueryFactory query;

    @Override
    public List<PostListResponse> getPostListOrderByCreatedAt(SearchType searchType, String keyword, Long last) {

        return query.select(
                        new QPostListResponse(
                            new QAuthor(post.author.userId, post.author.profileImage, post.author.username),
                            post.title,
                            post.createdAt,
                            post.updatedAt,
                            post.likeCount))
                    .from(post)
                    .where(search(searchType, keyword), paging(last))
                    .limit(10)
                    .orderBy(post.createdAt.desc())
                    .fetch();
    }

    @Override
    public List<PostListResponse> getPostListOrderByLike(SearchType searchType, String keyword, Long last) {
        return query.select(new QPostListResponse(
                        new QAuthor(post.author.userId, post.author.profileImage, post.author.username),
                        post.title,
                        post.createdAt,
                        post.updatedAt,
                        post.likeCount
                    ))
                    .from(post)
                    .where(search(searchType, keyword), paging(last))
                    .limit(10)
                    .orderBy(post.likeCount.desc())
                    .fetch();
    }

    private BooleanExpression search(SearchType searchType, String keyword) {
        if (keyword == null || keyword.isEmpty()) {
            return null;
        } else {
            switch (searchType) {
                case TITLE -> {
                    return post.title.contains(keyword);
                }
                case AUTHOR -> {
                    return post.author.username.contains(keyword);
                }
                case CONTENT -> {
                    return post.content.contains(keyword);
                }
                default -> {
                    return null;
                }
            }
        }
    }

    private BooleanExpression paging(Long last) {
        if (last != -1) {
            return post.createdAt.loe(query.select(post.createdAt).from(post).where(post.postId.eq(last)));
        }
        return null;
    }
}
