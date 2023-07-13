package personal.bulletinborad.service;

import jakarta.annotation.PostConstruct;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import personal.bulletinborad.entity.Member;
import personal.bulletinborad.enumtype.Verification;
import personal.bulletinborad.exception.ExceptionMessage;
import personal.bulletinborad.exception.LoginException;
import personal.bulletinborad.exception.NotMatchedPasswordException;
import personal.bulletinborad.infrastructure.MemberRepository;
import personal.bulletinborad.mock.FakeMailSender;
import personal.bulletinborad.mock.FakePasswordEncoder;
import personal.bulletinborad.mock.FakeVerificationCodeRepository;

import static org.assertj.core.api.Assertions.*;
import static personal.bulletinborad.enumtype.Verification.*;
import static personal.bulletinborad.exception.ExceptionMessage.*;

@SpringBootTest
@Transactional
class LoginServiceTest {

    @Autowired LoginService loginService;
    @Autowired MemberRepository memberRepository;
    MemberService memberService;

    @PostConstruct
    void postConstruct() {
        FakePasswordEncoder passwordEncoder = new FakePasswordEncoder();
        memberService = new MemberService(memberRepository,
                new FakeMailSender(),
                new FakeVerificationCodeRepository(),
                passwordEncoder);
        loginService = new LoginService(memberRepository, passwordEncoder);
    }

    @Test
    void 로그인시_해당_아이디_없음() {
        assertThatThrownBy(() -> loginService.login("apple", "1234"))
                .isInstanceOf(LoginException.class)
                .hasMessage(NOT_EXIST_NICKNAME);
    }

    @Test
    void 인증되지_않은_사용자_로그인() {
        Long memberId = memberService.join("apple", "1234", "apple@example.com", "banana");
        Member member = memberRepository.findById(memberId).get();

        assertThat(member.getVerification()).isEqualTo(NONE);
        assertThatThrownBy(() -> loginService.login("apple", "1234"))
                .isInstanceOf(LoginException.class)
                .hasMessage(NOT_VERIFY);
    }

    @Test
    void 비밀번호_불일치() {
        Long memberId = memberService.join("apple", "1234", "apple@example.com", "banana");
        memberService.verify(memberId, FakeMailSender.SEND_MESSAGE);
        Member member = memberRepository.findById(memberId).get();

        assertThat(member.getVerification()).isEqualTo(COMPLETE);
        assertThatThrownBy(() -> loginService.login("apple", "1233"))
                .isInstanceOf(NotMatchedPasswordException.class)
                .hasMessage(NOT_MATCHED_PASSWORD);
    }

    @Test
    void 로그인_성공() {
        Long memberId = memberService.join("apple", "1234", "apple@example.com", "banana");
        memberService.verify(memberId, FakeMailSender.SEND_MESSAGE);
        Member findMember = memberRepository.findById(memberId).get();

        Member loginMember = loginService.login("apple", "1234");

        assertThat(findMember.getVerification()).isEqualTo(COMPLETE);
        assertThat(loginMember).isEqualTo(findMember);
    }
}