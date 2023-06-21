package personal.bulletinborad.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import personal.bulletinborad.entity.Member;
import personal.bulletinborad.exception.ExceptionMessage;
import personal.bulletinborad.exception.ExistMemberException;
import personal.bulletinborad.repository.MemberRepository;

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
        Optional<Member> findByLoginId = memberRepository.findByLoginId(loginId);
        if (findByLoginId.isPresent()) {
            throw new ExistMemberException(EXIST_LOGIN_ID);
        }

        Optional<Member> findByEmail = memberRepository.findByEmail(email);
        if (findByEmail.isPresent()) {
            throw new ExistMemberException(EXIST_EMAIL);
        }

        Optional<Member> findByNickname = memberRepository.findByNickname(nickname);
        if (findByNickname.isPresent()) {
            throw new ExistMemberException(EXIST_NICKNAME);
        }

        Member savedMember = memberRepository.save(new Member(loginId, password, email, nickname));
        log.info("로그인 가입요청 완료 = {}", loginId);

        return savedMember.getId();
    }
}
