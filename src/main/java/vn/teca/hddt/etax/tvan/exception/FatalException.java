package vn.teca.hddt.etax.tvan.exception;

import lombok.Getter;
import vn.teca.hddt.etax.tvan.model.enums.ErrorCode;

public class FatalException extends Exception {
    @Getter
    private ErrorCode errorCode;

    public FatalException(String message) {
        super(message);
    }

    public FatalException(String message, Throwable cause) {
        super(message, cause);
    }

    public FatalException(String message, ErrorCode errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public FatalException(ErrorCode errorCode) {
        super(errorCode.getValue());
        this.errorCode = errorCode;
    }
}
