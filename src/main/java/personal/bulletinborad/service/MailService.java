package personal.bulletinborad.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.time.Duration;
import java.util.Random;

@Slf4j
@Service
@RequiredArgsConstructor
public class MailService {

    private final JavaMailSender javaMailSender;
    private final RedisTemplate<String, String> redisTemplate;

    @Value("${spring.mail.username}")
    private String id;

    public String sendMessage(String to) throws Exception {
        String verificationCode = createKey();
        MimeMessage message = createMessage(to, verificationCode);

        try {
            javaMailSender.send(message);
            storeVerificationCode(to, verificationCode, 60 * 5L);
            log.info("메일 전송 성공: {}", to);
        } catch(MailException e){
            log.info("메일 전송 실패: {}", to);
            e.printStackTrace();
            throw new IllegalArgumentException(e);
        }
        return verificationCode;
    }

    private void storeVerificationCode(String to, String verificationCode, Long duration) {
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        Duration expireDuration = Duration.ofSeconds(duration);
        valueOperations.set(to, verificationCode, expireDuration);
    }

    private MimeMessage createMessage(String to, String verificationCode) throws MessagingException, UnsupportedEncodingException {
        log.info("보내는 대상: {}, 인증번호: {}", to, verificationCode);
        MimeMessage message = javaMailSender.createMimeMessage();

        message.addRecipients(MimeMessage.RecipientType.TO, to);
        message.setSubject("게시판 회원가입 인증 코드: ");

        String msg="";
        msg += "<h1 style=\"font-size: 30px; padding-right: 30px; padding-left: 30px;\">이메일 주소 확인</h1>";
        msg += "<p style=\"font-size: 17px; padding-right: 30px; padding-left: 30px;\">아래 확인 코드를 회원가입 화면에서 입력해주세요.</p>";
        msg += "<div style=\"padding-right: 30px; padding-left: 30px; margin: 32px 0 40px;\"><table style=\"border-collapse: collapse; border: 0; background-color: #F4F4F4; height: 70px; table-layout: fixed; word-wrap: break-word; border-radius: 6px;\"><tbody><tr><td style=\"text-align: center; vertical-align: middle; font-size: 30px;\">";
        msg += verificationCode;
        msg += "</td></tr></tbody></table></div>";

        message.setText(msg, "utf-8", "html");
        message.setFrom(new InternetAddress(id,"게시판 관리자"));

        return message;
    }

    private String createKey() {
        StringBuffer key = new StringBuffer();
        Random random = new Random();

        for (int i = 0; i < 6; i++) { // 인증코드 6자리
            key.append((random.nextInt(10)));
        }
        return key.toString();
    }
}