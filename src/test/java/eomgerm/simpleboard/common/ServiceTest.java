package eomgerm.simpleboard.common;

import eomgerm.simpleboard.auth.service.AuthService;
import eomgerm.simpleboard.auth.utils.JwtTokenProvider;
import eomgerm.simpleboard.post.domain.PostRepository;
import eomgerm.simpleboard.user.domain.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
public class ServiceTest {

    @Autowired
    protected DatabaseCleaner databaseCleaner;

    @Autowired
    protected AuthService authService;

    @Autowired
    protected UserRepository userRepository;

    @Autowired
    protected JwtTokenProvider jwtTokenProvider;

    @Autowired
    protected PostRepository postRepository;

    @BeforeEach
    void setUp() {
        databaseCleaner.execute();
    }
}
