package eomgerm.simpleboard.user.service;

import eomgerm.simpleboard.global.base.BaseException;
import eomgerm.simpleboard.user.domain.User;
import eomgerm.simpleboard.user.domain.UserRepository;
import eomgerm.simpleboard.user.exception.UserErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserFindService {

    private final UserRepository userRepository;

    public User findById(Long userId) {
        return userRepository.findById(userId)
                             .orElseThrow(() -> BaseException.of(UserErrorCode.USER_NOT_FOUND));
    }
}
