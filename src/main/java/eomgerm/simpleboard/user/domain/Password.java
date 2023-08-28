package eomgerm.simpleboard.user.domain;

import eomgerm.simpleboard.global.base.BaseException;
import eomgerm.simpleboard.user.exception.UserErrorCode;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.regex.Pattern;

@Getter
@NoArgsConstructor
@Embeddable
public class Password {
    private static final String PASSWORD_PATTERN = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#%^&*()_+-=\\[\\]{}|;':\",./<>?~`\\\\])[A-Za-z\\d!@#%^&*()_+\\-=\\[\\]{}|;':\",./<>?~`\\\\]{9,16}";
    private static final Pattern PASSWORD_MATCHER = Pattern.compile(PASSWORD_PATTERN);

    @Column(name = "password", nullable = false, length = 200)
    private String value;

    private Password(String value) {
        this.value = value;
    }

    public static Password encrypt(String value, PasswordEncoder encoder) {
        validatePassword(value);
        return new Password(encoder.encode(value));
    }

    private static void validatePassword(String value) {
        if (isNotValidPassword(value)) {
            throw BaseException.of(UserErrorCode.INVALID_PASSWORD_PATTERN);
        }
    }

    private static boolean isNotValidPassword(String password) {
        return !PASSWORD_MATCHER.matcher(password).matches();
    }

    public boolean isSamePassword(String compare, PasswordEncoder encoder) {
        return encoder.matches(compare, this.value);
    }
}
