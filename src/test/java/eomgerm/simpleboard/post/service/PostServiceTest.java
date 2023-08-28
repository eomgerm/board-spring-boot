package eomgerm.simpleboard.post.service;

import static eomgerm.simpleboard.fixture.PostFixture.DOG;
import static eomgerm.simpleboard.fixture.UserFixture.ALEX;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import eomgerm.simpleboard.common.ServiceTest;
import eomgerm.simpleboard.post.domain.Post;
import eomgerm.simpleboard.user.domain.User;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@DisplayName("BoardService 테스트")
class PostServiceTest extends ServiceTest {

    @Autowired
    private PostService postService;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");


    private User author;

    @BeforeEach
    void setUp() {
        author = userRepository.save(ALEX.toUser());
    }

    @Nested
    @DisplayName("게시물 작성")
    class createPost {

        @Test
        @DisplayName("게시글 등록 성공")
        void success() {
            Long postId = postService.createPost(DOG.getTitle(), DOG.getContent(), author.getUserId());
            Post findPost = postRepository.findById(postId).orElseThrow();
            assertAll(
                () -> assertThat(findPost.getAuthor().getUserId()).isEqualTo(author.getUserId()),
                () -> assertThat(findPost.getContent()).isEqualTo(DOG.getContent()),
                () -> assertThat(findPost.getCreatedAt().format(formatter)).isEqualTo(LocalDateTime.now().format(formatter)),
                () -> assertThat(findPost.getUpdatedAt().format(formatter)).isEqualTo(LocalDateTime.now().format(formatter))
            );

        }
    }
}