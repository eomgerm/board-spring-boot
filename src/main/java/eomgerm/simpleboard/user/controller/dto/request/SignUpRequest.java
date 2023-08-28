package eomgerm.simpleboard.user.controller.dto.request;

import static eomgerm.simpleboard.global.utils.PasswordEncoderUtils.ENCODER;

import eomgerm.simpleboard.user.domain.Password;
import eomgerm.simpleboard.user.domain.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record SignUpRequest(
    @NotBlank(message = "이메일은 필수입니다.")
    @Email(message = "이메일 형식이 아닙니다.")
    String email,

    @NotBlank(message = "비밀번호는 필수입니다.")
    String password,

    @NotBlank(message = "닉네임은 필수입니다.")
    String username
) {

    public User toUser() {
        return User.createUser(email, Password.encrypt(password, ENCODER), username);
    }
}
