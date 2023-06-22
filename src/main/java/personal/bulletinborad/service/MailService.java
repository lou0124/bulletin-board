package personal.bulletinborad.service;

import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import personal.bulletinborad.infrastructure.VerificationCodeRepository;
import personal.bulletinborad.infrastructure.VerificationMailSender;

import java.io.UnsupportedEncodingException;


@Slf4j
@Service
@RequiredArgsConstructor
public class MailService {

    private final VerificationMailSender mailSender;
    private final VerificationCodeRepository repository;

    public String sendMessage(String to) throws MessagingException, UnsupportedEncodingException {
        String code = mailSender.send(to);
        repository.save(to, code, 60 * 5L);
        log.info("{} 에게 {} 코드 전송 및 저장", to, code);
        return code;
    }

    public boolean verify(String email, String code) {
        String storedCode = repository.findByEmail(email);
        if (storedCode == null) {
            return false;
        }
        return storedCode.equals(code);
    }
}