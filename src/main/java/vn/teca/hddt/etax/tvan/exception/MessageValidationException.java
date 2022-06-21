package vn.teca.hddt.etax.tvan.exception;

public class MessageValidationException extends Exception {
    public MessageValidationException(String message) {
        super(message);
    }

    public MessageValidationException(String message, Throwable cause) {
        super(message, cause);
    }
}