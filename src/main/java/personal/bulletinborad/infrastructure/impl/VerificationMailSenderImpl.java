package personal.bulletinborad.infrastructure.impl;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;
import personal.bulletinborad.exception.MailException;
import personal.bulletinborad.infrastructure.VerificationMailSender;

import java.io.UnsupportedEncodingException;
import java.util.Random;

@Slf4j
@Component
@RequiredArgsConstructor
public class VerificationMailSenderImpl implements VerificationMailSender {

    private final JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String id;

    @Override
    public String send(String to) {
        String code = createKey();

        MimeMessage message = null;
        try {
            message = createMessage(to, code);
        } catch (Exception e) {
            log.info("메세지 생성에 실패했습니다", e);
            e.printStackTrace();
        }

        try {
            javaMailSender.send(message);
        } catch (Exception e) {
            throw new MailException("이메일을 보낼 수 없습니다. 이메일을 올바르게 입력하였는지 확인해 주세요.", e);
        }

        return code;
    }

    private MimeMessage createMessage(String to, String code) throws MessagingException, UnsupportedEncodingException {
        log.info("보내는 대상: {}, 인증번호: {}", to, code);
        MimeMessage message = javaMailSender.createMimeMessage();

        message.addRecipients(MimeMessage.RecipientType.TO, to);
        message.setSubject("게시판 회원가입 인증 코드: ");

        String msg="";
        msg += "<h1 style=\"font-size: 30px; padding-right: 30px; padding-left: 30px;\">이메일 주소 확인</h1>";
        msg += "<p style=\"font-size: 17px; padding-right: 30px; padding-left: 30px;\">아래 확인 코드를 회원가입 화면에서 입력해주세요.</p>";
        msg += "<div style=\"padding-right: 30px; padding-left: 30px; margin: 32px 0 40px;\"><table style=\"border-collapse: collapse; border: 0; background-color: #F4F4F4; height: 70px; table-layout: fixed; word-wrap: break-word; border-radius: 6px;\"><tbody><tr><td style=\"text-align: center; vertical-align: middle; font-size: 30px;\">";
        msg += code;
        msg += "</td></tr></tbody></table></div>";

        message.setText(msg, "utf-8", "html");
        message.setFrom(new InternetAddress(id,"게시판 관리자"));

        return message;
    }

    private String createKey() {
        StringBuffer key = new StringBuffer();
        Random random = new Random();

        for (int i = 0; i < 6; i++) {
            key.append((random.nextInt(10)));
        }
        return key.toString();
    }
}
