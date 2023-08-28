package eomgerm.simpleboard.post.exception;

import eomgerm.simpleboard.global.base.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum PostErrorCode implements ErrorCode {
    INVALID_SORT_CONDITION(HttpStatus.BAD_REQUEST, "POST-001", "정렬 조건이 유효하지 않습니다."),
    INVALID_SEARCH_TYPE(HttpStatus.BAD_REQUEST, "POST-002", "검색 유형이 유효하지 않습니다.");

    private final HttpStatus status;
    private final String errorCode;
    private final String message;
}
