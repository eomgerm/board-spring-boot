package eomgerm.simpleboard.global.base;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum GlobalErrorCode implements ErrorCode {

    SUCCESS_OK(HttpStatus.OK, "SUCCESS", "요청 성공"),
    SUCCESS_CREATED(HttpStatus.CREATED, "SUCCESS", "요청 성공 및 리소스 생성됨"),
    SUCCESS_ACCEPTED(HttpStatus.ACCEPTED, "SUCCESS", "요청 성공"),
    INVALID_JWT(HttpStatus.UNAUTHORIZED, "JWT-001", "JWT 인증 실패"),
    INVALID_USER_JWT(HttpStatus.FORBIDDEN, "JWT-002", "권한이 없습니다."),
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "JWT-003", "자격 증명이 이루어지지 않았습니다"),
    VALIDATION_ERROR(HttpStatus.BAD_REQUEST, "REQ-001", "");

    private final HttpStatus status;
    private final String errorCode;
    private final String message;
}

