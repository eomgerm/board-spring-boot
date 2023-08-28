package eomgerm.simpleboard.user.service;

import eomgerm.simpleboard.global.base.BaseException;
import eomgerm.simpleboard.user.domain.User;
import eomgerm.simpleboard.user.domain.UserRepository;
import eomgerm.simpleboard.user.exception.UserErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public Long saveUser(User user) {
        validateDuplicateEmail(user.getEmail());

        userRepository.save(user);

        return user.getUserId();
    }

    private void validateDuplicateEmail(String email) {
        if (userRepository.existsByEmail(email)) {
            throw BaseException.of(UserErrorCode.DUPLICATE_EMAIL);
        }
    }
}
