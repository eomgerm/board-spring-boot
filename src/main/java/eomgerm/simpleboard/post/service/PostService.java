package eomgerm.simpleboard.post.service;

import eomgerm.simpleboard.post.domain.Post;
import eomgerm.simpleboard.post.domain.PostRepository;
import eomgerm.simpleboard.user.domain.User;
import eomgerm.simpleboard.user.service.UserFindService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PostService {

    private final UserFindService userFindService;
    private final PostRepository postRepository;

    @Transactional()
    public Long createPost(String title, String content, Long userId) {
        User author = userFindService.findById(userId);
        Post newPost = Post.createPost(title, content, author);
        return postRepository.save(newPost).getPostId();
    }

}
