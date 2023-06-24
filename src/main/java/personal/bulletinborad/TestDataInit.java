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
        Member member1 = new Member("test", "1234", "abc@naver.com", "apple");
        Member member2 = new Member("test2", "1234", "abc2@naver.com", "banana");
        member1.verify();
        member2.verify();
        memberRepository.save(member1);
        memberRepository.save(member2);

        for (int i = 1; i <= 200; i++) {
            if (i % 2 == 0) {
                postRepository.save(new Post(member1, null, "title" + i, "content" + i, 0, null));
            } else {
                postRepository.save(new Post(member2, null, "title" + i, "content" + i, 0, null));
            }
        }
    }
}
