package personal.bulletinborad.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import personal.bulletinborad.entity.Member;
import personal.bulletinborad.infrastructure.MemberRepository;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LoginService {

    private final MemberRepository memberRepository;

    public Member login(String loginId, String password) {
        Optional<Member> optionalMember = memberRepository.findByLoginId(loginId);

        if (optionalMember.isEmpty()) {
            throw new IllegalArgumentException("해당 아이디가 존재하지 않습니다.");
        }

        Member member = optionalMember.get();

        if (member.getPassword().equals(password)) {
            return member;
        } else  {
            return null;
        }
    }
}
