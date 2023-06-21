package personal.bulletinborad.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import personal.bulletinborad.entity.Member;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByLoginId(String loginId);

    Optional<Member> findByEmail(String email);

    Optional<Member> findByNickname(String nickname);
}
