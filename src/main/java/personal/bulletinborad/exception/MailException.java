package personal.bulletinborad.exception;

public class MailException extends RuntimeException {
    public MailException(String message, Throwable cause) {
        super(message, cause);
    }
}
