package personal.bulletinborad.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;
import personal.bulletinborad.entity.Member;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByLoginIdOrEmailOrNickname(String loginId, String email, String nickname);
}
