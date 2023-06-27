package personal.bulletinborad;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import personal.bulletinborad.entity.Comment;
import personal.bulletinborad.entity.Member;
import personal.bulletinborad.entity.Post;
import personal.bulletinborad.infrastructure.CommentRepository;
import personal.bulletinborad.infrastructure.MemberRepository;
import personal.bulletinborad.infrastructure.PostRepository;

@Profile("local")
@Component
@RequiredArgsConstructor
public class TestDataInit {

    private final MemberRepository memberRepository;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    @EventListener(ApplicationReadyEvent.class)
    public void initData() {
        Member member1 = new Member("test", "1234", "abc@naver.com", "apple");
        Member member2 = new Member("test2", "1234", "abc2@naver.com", "banana");
        member1.verify();
        member2.verify();
        memberRepository.save(member1);
        memberRepository.save(member2);

        for (int i = 1; i <= 200; i++) {
            Post post;
            if (i % 2 == 0) {
                post = postRepository.save(new Post(member1, null, "title" + i, "content" + i, 0, null));
            } else {
                post = postRepository.save(new Post(member2, null, "title" + i, "content" + i, 0, null));
            }
            addComment(member1, member2, i, post);
        }
    }

    private void addComment(Member member1, Member member2, int i, Post post) {
        for (int j = 1; j <= 20; j++) {
            if (j % 2 == 0) {
                commentRepository.save(new Comment("content" + j, member1, post));
            } else {
                commentRepository.save(new Comment("content" + j, member2, post));
            }
        }
    }
}
