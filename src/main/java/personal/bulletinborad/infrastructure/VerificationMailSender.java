package personal.bulletinborad.infrastructure;

import jakarta.mail.MessagingException;

import java.io.UnsupportedEncodingException;

public interface VerificationMailSender {

    String send(String to) ;
}
