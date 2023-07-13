package personal.bulletinborad.mock;

import jakarta.mail.MessagingException;
import personal.bulletinborad.infrastructure.VerificationMailSender;

import java.io.UnsupportedEncodingException;

public class FakeMailSender implements VerificationMailSender {

    public static final String SEND_MESSAGE = "123456";

    @Override
    public String send(String to) {
        return SEND_MESSAGE;
    }
}
