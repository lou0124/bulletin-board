package personal.bulletinborad;

import jakarta.mail.MessagingException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.transaction.annotation.Transactional;
import personal.bulletinborad.infrastructure.VerificationCodeRepositoryImpl;
import personal.bulletinborad.infrastructure.VerificationMailSenderImpl;

import java.io.UnsupportedEncodingException;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
class MailSendAndRedisStoreTest {

    @Autowired
    private VerificationMailSenderImpl mailSender;
    @Autowired
    private VerificationCodeRepositoryImpl repository;

    @Test
    void 메일보내고_저장하고_찾기() throws MessagingException, UnsupportedEncodingException {
        String to = "ohchangmin00@gmail.com";
        String code1 = mailSender.send(to);
        repository.save(to, code1, 60L);
        String code2 = repository.findByEmail(to);
        System.out.println("code2 = " + code2);
        Assertions.assertThat(code1).isEqualTo(code2);
    }
}
