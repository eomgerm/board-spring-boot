package eomgerm.simpleboard.fixture;

import static eomgerm.simpleboard.global.utils.PasswordEncoderUtils.ENCODER;

import eomgerm.simpleboard.user.domain.Password;
import eomgerm.simpleboard.user.domain.User;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum UserFixture {
    ALEX("alex@naver.com", "Testing123!", "알렉스");

    private final String email;
    private final String password;
    private final String username;

    public User toUser() {
        return User.createUser(this.email, Password.encrypt(password, ENCODER), this.username);
    }
}
