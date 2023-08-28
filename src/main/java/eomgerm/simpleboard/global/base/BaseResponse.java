package eomgerm.simpleboard.global.base;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@JsonPropertyOrder({"status, errorCode, message, result"})
public class BaseResponse<T> {

    private final int status;
    private final String errorCode;
    private final String message;
    @JsonInclude(Include.NON_NULL)
    private T result;

    public BaseResponse(T result) {
        this.status = GlobalErrorCode.SUCCESS_OK.getStatus().value();
        this.errorCode = GlobalErrorCode.SUCCESS_OK.getErrorCode();
        this.message = GlobalErrorCode.SUCCESS_OK.getMessage();
        this.result = result;
    }

    public BaseResponse(ErrorCode status, T result) {
        this.status = status.getStatus().value();
        this.errorCode = status.getErrorCode();
        this.message = status.getMessage();
        this.result = result;
    }
}
