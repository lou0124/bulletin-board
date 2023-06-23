package personal.bulletinborad.exception;

public class MailException extends RuntimeException {
    public MailException() {
    }

    public MailException(String message) {
        super(message);
    }

    public MailException(Throwable cause) {
        super(cause);
    }
}
