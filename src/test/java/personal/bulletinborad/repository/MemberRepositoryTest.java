package personal.bulletinborad.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import personal.bulletinborad.entity.Member;
import personal.bulletinborad.entity.Post;

import static org.assertj.core.api.Assertions.*;

@DataJpaTest
class MemberRepositoryTest {

    @Autowired
    MemberRepository memberRepository;

    @Test
    void findByLoginId_작동_확인() {
        //given
        String loginId = "user1";
        Member member = new Member(loginId, "1234", "user@example.com", "nickname1");
        memberRepository.save(member);

        //when
        Member findMember = memberRepository.findByLoginId(loginId).get();
        //then
        assertThat(findMember.getLoginId()).isEqualTo(loginId);
    }
}