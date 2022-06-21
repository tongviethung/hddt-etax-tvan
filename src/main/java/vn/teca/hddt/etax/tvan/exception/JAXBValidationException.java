package vn.teca.hddt.etax.tvan.exception;

import lombok.Getter;
import vn.teca.hddt.etax.tvan.model.enums.ErrorCode;

public class JAXBValidationException extends Exception {
    @Getter
    private ErrorCode errorCode;

    public JAXBValidationException(String message) {
        super(message);
    }

    public JAXBValidationException(String message, ErrorCode errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
}
