package eomgerm.simpleboard.global.base;

import lombok.Getter;

@Getter
public class BaseException extends RuntimeException {

    private final ErrorCode code;

    public BaseException(ErrorCode code) {
        super(code.getMessage());
        this.code = code;
    }

    public static BaseException of(ErrorCode code) {
        return new BaseException(code);
    }
}
