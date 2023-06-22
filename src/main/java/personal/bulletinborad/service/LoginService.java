package personal.bulletinborad.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import personal.bulletinborad.entity.Member;
import personal.bulletinborad.exception.ExceptionMessage;
import personal.bulletinborad.exception.LoginException;
import personal.bulletinborad.infrastructure.MemberRepository;

import java.util.Optional;

import static personal.bulletinborad.exception.ExceptionMessage.*;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LoginService {

    private final MemberRepository memberRepository;

    public Member login(String loginId, String password) {
        Optional<Member> optionalMember = memberRepository.findByLoginId(loginId);

        if (optionalMember.isEmpty()) {
            throw new LoginException(NOT_EXIST_NICKNAME);
        }

        Member member = optionalMember.get();

        if (!member.isVerified()) {
            throw new LoginException(NOT_VERIFY);
        }

        if (member.isMatchedPassword(password)) {
            return member;
        } else  {
            return null;
        }
    }
}
