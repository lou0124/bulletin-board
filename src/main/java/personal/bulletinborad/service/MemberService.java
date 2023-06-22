package personal.bulletinborad.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import personal.bulletinborad.entity.Member;
import personal.bulletinborad.exception.ExistMemberException;
import personal.bulletinborad.infrastructure.repository.MemberRepository;

import java.util.Optional;

import static personal.bulletinborad.exception.ExceptionMessage.*;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;

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

        return savedMember.getId();
    }

    private void checkExist(String s1, String s2, String message) {
        if (s1.equals(s2)) {
            throw new ExistMemberException(message);
        }
    }
}
