package personal.bulletinborad.service;

import jakarta.mail.MessagingException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import personal.bulletinborad.infrastructure.VerificationCodeRepository;
import personal.bulletinborad.infrastructure.VerificationMailSender;
import personal.bulletinborad.mock.FakeMailSender;
import personal.bulletinborad.mock.FakeVerificationCodeRepository;

import static org.assertj.core.api.Assertions.*;

class MailServiceTest {

    MailService mailService = new MailService(new FakeMailSender(), new FakeVerificationCodeRepository());

    @Test
    void 메일_보내고_검증까지() throws Exception {
        String email = "aaa@example.com";
        String code = mailService.sendMessage(email);
        boolean verify = mailService.verify(email, code);
        assertThat(verify).isTrue();
    }

    @Test
    void 없는_이메일을_검증() {
        boolean verify = mailService.verify("aaa@example.com", "123456");
        assertThat(verify).isFalse();
    }
}