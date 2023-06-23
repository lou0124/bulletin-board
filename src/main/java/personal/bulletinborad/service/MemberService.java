package personal.bulletinborad.service;

import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import personal.bulletinborad.entity.Member;
import personal.bulletinborad.exception.ExistMemberException;
import personal.bulletinborad.infrastructure.MemberRepository;
import personal.bulletinborad.infrastructure.VerificationCodeRepository;
import personal.bulletinborad.infrastructure.VerificationMailSender;

import java.io.UnsupportedEncodingException;
import java.util.Optional;

import static personal.bulletinborad.exception.ExceptionMessage.*;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;
    private final VerificationMailSender mailSender;
    private final VerificationCodeRepository verificationCodeRepository;

    @Transactional
    public Long join(String loginId, String password, String email, String nickname) {

        Optional<Member> optionalMember = memberRepository.findByLoginIdOrEmailOrNickname(loginId, email, nickname);
        if (optionalMember.isPresent()) {
            Member member = optionalMember.get();
            checkExist(member.getLoginId(), loginId, EXIST_LOGIN_ID);
            checkExist(member.getEmail(), email, EXIST_EMAIL);
            checkExist(member.getNickname(), nickname, EXIST_NICKNAME);
        }

        Member savedMember = memberRepository.save(new Member(loginId, password, email, nickname));
        log.info("로그인 가입요청 완료 = {}", loginId);

        sendMessage(email);

        return savedMember.getId();
    }

    @Transactional
    public boolean verify(Long memberId, String code) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("해당 멤버가 가입신청 되지 않았습니다."));

        String storedCode = verificationCodeRepository.findByEmail(member.getEmail());

        if (storedCode == null || !storedCode.equals(code)) {
            return false;
        }

        member.verify();
        return true;
    }

    private void checkExist(String s1, String s2, String message) {
        if (s1.equals(s2)) {
            throw new ExistMemberException(message);
        }
    }

    private void sendMessage(String to) {
        String code = mailSender.send(to);
        verificationCodeRepository.save(to, code, 60 * 5L);
        log.info("{} 에게 {} 코드 전송 및 저장", to, code);
    }
}
