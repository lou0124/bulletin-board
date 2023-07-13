package personal.bulletinborad.exception;

public class NotMatchedPasswordException extends RuntimeException {
    public NotMatchedPasswordException() {
    }

    public NotMatchedPasswordException(String message) {
        super(message);
    }
}
