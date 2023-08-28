package eomgerm.simpleboard.auth.service;

import static eomgerm.simpleboard.fixture.UserFixture.ALEX;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import eomgerm.simpleboard.auth.controller.dto.response.LoginResponse;
import eomgerm.simpleboard.auth.exception.AuthErrorCode;
import eomgerm.simpleboard.common.ServiceTest;
import eomgerm.simpleboard.global.base.BaseException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@DisplayName("AuthService 테스트")
class AuthServiceTest extends ServiceTest {

    @Nested
    @DisplayName("로그인")
    class login {

        private Long userId;

        @BeforeEach
        void setUp() {
            userId = userRepository.save(ALEX.toUser()).getUserId();
        }

        @Test
        @DisplayName("비밀번호가 일치하지 않으면 로그인 실패")
        void throwExceptionByWrongPassword() {
            assertThatThrownBy(() -> authService.login(ALEX.getEmail(), "wrong" + ALEX.getPassword()))
                .isInstanceOf(BaseException.class)
                .hasMessage(AuthErrorCode.INVALID_LOGIN_REQUEST.getMessage());
        }

        @Test
        @DisplayName("로그인 성공")
        void success() {
            //when
            LoginResponse loginResponse = authService.login(ALEX.getEmail(), ALEX.getPassword());

            //then
            assertAll(
                () -> assertThat(loginResponse).isNotNull(),
                () -> assertThat(jwtTokenProvider.getId(loginResponse.accessToken())).isEqualTo(userId),
                () -> assertThat(jwtTokenProvider.getId(loginResponse.refreshToken())).isEqualTo(userId),
                () -> {
                    String dbRefreshToken = userRepository.findRefreshTokenByUserId(userId).orElseThrow();
                    assertThat(dbRefreshToken).isEqualTo(loginResponse.refreshToken());
                }
            );
        }

    }
}