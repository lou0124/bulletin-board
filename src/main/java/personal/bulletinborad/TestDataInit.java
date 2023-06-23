package personal.bulletinborad;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import personal.bulletinborad.entity.Member;
import personal.bulletinborad.entity.Post;
import personal.bulletinborad.infrastructure.MemberRepository;
import personal.bulletinborad.infrastructure.PostRepository;

@Profile("local")
@Component
@RequiredArgsConstructor
public class TestDataInit {

    private final MemberRepository memberRepository;
    private final PostRepository postRepository;

    @EventListener(ApplicationReadyEvent.class)
    public void initData() {
        Member member = new Member("test", "1234", "abc@naver.com", "apple");
        member.verify();
        memberRepository.save(member);

        for (int i = 0; i < 100; i++) {
            postRepository.save(new Post(member, null, "title" + i, "content" + i, 0, null));
        }
    }
}
