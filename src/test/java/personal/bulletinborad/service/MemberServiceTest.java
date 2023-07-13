package personal.bulletinborad.service;

import jakarta.annotation.PostConstruct;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import personal.bulletinborad.entity.Member;
import personal.bulletinborad.enumtype.Verification;
import personal.bulletinborad.exception.ExistMemberException;
import personal.bulletinborad.infrastructure.MemberRepository;
import personal.bulletinborad.mock.FakeMailSender;
import personal.bulletinborad.mock.FakePasswordEncoder;
import personal.bulletinborad.mock.FakeVerificationCodeRepository;

import static org.assertj.core.api.Assertions.*;
import static personal.bulletinborad.exception.ExceptionMessage.*;

@SpringBootTest
@Transactional
class MemberServiceTest {

    @Autowired MemberRepository memberRepository;
    MemberService memberService;

    String loginId = "apple";
    String password = "1234";
    String email = "apple@example.com";
    String nickname = "banana";

    @PostConstruct
    void postConstruct() {
        memberService = new MemberService(
                memberRepository,
                new FakeMailSender(),
                new FakeVerificationCodeRepository(),
                new FakePasswordEncoder()
        );
    }

    @Test
    void join_수행_확인() {
        //when
        Long memberId = memberService.join(loginId, password, email, nickname);

        //then
        Member member = memberRepository.findById(memberId).get();
        assertThat(member.getLoginId()).isEqualTo(loginId);
        assertThat(member.getPassword()).isEqualTo(password);
        assertThat(member.getEmail()).isEqualTo(email);
        assertThat(member.getNickname()).isEqualTo(nickname);
    }

    @Test
    void 이미_존재하는_로그인아이디_에러반환() throws Exception {
        memberService.join(loginId, password, email, nickname);

        //when //then
        assertThatThrownBy(() -> memberService.join(loginId, "password", "email", "nickname"))
                .isInstanceOf(ExistMemberException.class)
                .hasMessage(EXIST_LOGIN_ID);
    }

    @Test
    void 이미_존재하는_이메일_에러반환() throws Exception {
        memberService.join(loginId, password, email, nickname);

        //when //then
        assertThatThrownBy(() -> memberService.join("loginId", "password", email, "nickname"))
                .isInstanceOf(ExistMemberException.class)
                .hasMessage(EXIST_EMAIL);
    }

    @Test
    void 이미_존재하는_닉네임_에러반환() throws Exception {
        memberService.join(loginId, password, email, nickname);

        //when //then
        assertThatThrownBy(() -> memberService.join("loginId", "password", "email", nickname))
                .isInstanceOf(ExistMemberException.class)
                .hasMessage(EXIST_NICKNAME);
    }

    @Test
    void join후_인증_성공() throws Exception {
        Long memberId = memberService.join(loginId, password, email, nickname);
        boolean verify = memberService.verify(memberId, FakeMailSender.SEND_MESSAGE);
        Member member = memberRepository.findById(memberId).get();
        assertThat(verify).isTrue();
        assertThat(member.getVerification()).isEqualTo(Verification.COMPLETE);
    }

    @Test
    void join후_인증_실패() throws Exception {
        Long memberId = memberService.join(loginId, password, email, nickname);
        boolean verify = memberService.verify(memberId, "xxxxxx");
        assertThat(verify).isFalse();
    }
}