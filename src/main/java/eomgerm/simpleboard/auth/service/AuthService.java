package eomgerm.simpleboard.auth.service;

import eomgerm.simpleboard.auth.controller.dto.response.LoginResponse;
import eomgerm.simpleboard.auth.exception.AuthErrorCode;
import eomgerm.simpleboard.auth.utils.JwtTokenProvider;
import eomgerm.simpleboard.global.base.BaseException;
import eomgerm.simpleboard.global.utils.PasswordEncoderUtils;
import eomgerm.simpleboard.user.domain.Password;
import eomgerm.simpleboard.user.domain.User;
import eomgerm.simpleboard.user.domain.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class AuthService {

    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;


    @Transactional
    public LoginResponse login(String email, String password) {

        User user = userRepository.findByEmail(email)
                                  .orElseThrow(() -> BaseException.of(AuthErrorCode.INVALID_LOGIN_REQUEST));
        if (!validatePassword(password, user.getPassword())) {
            throw BaseException.of(AuthErrorCode.INVALID_LOGIN_REQUEST);
        }

        String accessToken = jwtTokenProvider.createAccessToken(user.getUserId());
        String refreshToken = jwtTokenProvider.createRefreshToken(user.getUserId());

        user.updateRefreshToken(refreshToken);

        return LoginResponse.builder()
                            .accessToken(accessToken)
                            .refreshToken(refreshToken)
                            .build();
    }

    private boolean validatePassword(String comparePassword, Password savedPassword) {
        return savedPassword.isSamePassword(comparePassword, PasswordEncoderUtils.ENCODER);
    }
}
