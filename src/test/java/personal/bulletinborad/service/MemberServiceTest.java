package personal.bulletinborad.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import personal.bulletinborad.entity.Member;
import personal.bulletinborad.exception.ExistMemberException;
import personal.bulletinborad.infrastructure.repository.MemberRepository;

import static org.assertj.core.api.Assertions.*;
import static personal.bulletinborad.exception.ExceptionMessage.*;

@SpringBootTest
@Transactional
class MemberServiceTest {

    @Autowired MemberService memberService;
    @Autowired MemberRepository memberRepository;

    @Test
    void join_수행_확인() {
        //given
        String loginId = "apple";
        String password = "1234";
        String email = "apple@example.com";
        String nickname = "banana";

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
    void 이미_존재하는_로그인아이디_에러반환() {
        //given
        String loginId = "apple";
        String password = "1234";
        String email = "apple@example.com";
        String nickname = "banana";
        memberService.join(loginId, password, email, nickname);

        //when //then
        assertThatThrownBy(() -> memberService.join(loginId, "password", "email", "nickname"))
                .isInstanceOf(ExistMemberException.class)
                .hasMessage(EXIST_LOGIN_ID);
    }

    @Test
    void 이미_존재하는_이메일_에러반환() {
        //given
        String loginId = "apple";
        String password = "1234";
        String email = "apple@example.com";
        String nickname = "banana";
        memberService.join(loginId, password, email, nickname);

        //when //then
        assertThatThrownBy(() -> memberService.join("loginId", "password", email, "nickname"))
                .isInstanceOf(ExistMemberException.class)
                .hasMessage(EXIST_EMAIL);
    }

    @Test
    void 이미_존재하는_닉네임_에러반환() {
        //given
        String loginId = "apple";
        String password = "1234";
        String email = "apple@example.com";
        String nickname = "banana";
        memberService.join(loginId, password, email, nickname);

        //when //then
        assertThatThrownBy(() -> memberService.join("loginId", "password", "email", nickname))
                .isInstanceOf(ExistMemberException.class)
                .hasMessage(EXIST_NICKNAME);
    }
}