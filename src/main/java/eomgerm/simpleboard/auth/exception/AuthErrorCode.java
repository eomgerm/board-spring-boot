package eomgerm.simpleboard.auth.exception;

import eomgerm.simpleboard.global.base.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum AuthErrorCode implements ErrorCode {
    AUTH_EXPIRED_TOKEN(HttpStatus.UNAUTHORIZED, "AUTH-001", "토큰 만료"),
    AUTH_INVALID_TOKEN(HttpStatus.UNAUTHORIZED, "AUTH_002", "토큰이 유효하지 않습니다."),
    INVALID_LOGIN_REQUEST(HttpStatus.UNAUTHORIZED, "AUTH-003", "올바른 이메일이나 패스워드가 아닙니다.");

    private final HttpStatus status;
    private final String errorCode;
    private final String message;
}
