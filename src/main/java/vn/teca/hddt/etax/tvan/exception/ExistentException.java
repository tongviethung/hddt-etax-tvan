package vn.teca.hddt.etax.tvan.exception;

import lombok.Getter;
import vn.teca.hddt.etax.tvan.model.enums.ErrorCode;

public class ExistentException extends Exception {
    @Getter
    private ErrorCode errorCode;

    public ExistentException(String message) {
        super(message);
    }

    public ExistentException(String message, Throwable cause) {
        super(message, cause);
    }

    public ExistentException(String message, ErrorCode errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
}
