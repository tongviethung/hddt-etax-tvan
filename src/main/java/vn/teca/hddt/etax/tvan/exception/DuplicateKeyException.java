package vn.teca.hddt.etax.tvan.exception;

public class DuplicateKeyException extends Exception {
    public DuplicateKeyException(String message) {
        super(message);
    }

    public DuplicateKeyException(String message, Throwable cause) {
        super(message, cause);
    }
}

