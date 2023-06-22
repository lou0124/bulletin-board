package personal.bulletinborad.mock;

import jakarta.mail.MessagingException;
import personal.bulletinborad.infrastructure.VerificationMailSender;

import java.io.UnsupportedEncodingException;

public class FakeMailSender implements VerificationMailSender {

    @Override
    public String send(String to) throws MessagingException, UnsupportedEncodingException {
        return "123456";
    }
}
