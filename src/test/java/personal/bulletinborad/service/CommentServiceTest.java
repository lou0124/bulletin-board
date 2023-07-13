package personal.bulletinborad.service;


import jakarta.annotation.PostConstruct;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import personal.bulletinborad.entity.Comment;
import personal.bulletinborad.entity.Member;
import personal.bulletinborad.entity.Post;
import personal.bulletinborad.infrastructure.CommentRepository;
import personal.bulletinborad.infrastructure.MemberRepository;
import personal.bulletinborad.infrastructure.PostRepository;

import java.util.NoSuchElementException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class CommentServiceTest {

    @Autowired
    CommentService commentService;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    PostRepository postRepository;
    @Autowired
    CommentRepository commentRepository;

    Member member = new Member("loginId", "password", "email@email.com", "nickname");
    Post post = new Post(member, null, "title", "content", 0, null);

    @BeforeEach
    void beforeEach() {
        memberRepository.save(member);
        postRepository.save(post);
    }

    @Test
    void 댓글_쓰기_성공() {
        Long commentId = commentService.write(member, post.getId(), "commentContent");
        Comment comment = commentRepository.findById(commentId).get();
        assertThat(commentId).isEqualTo(comment.getId());
    }

    @Test
    void write_시_post없는경우_예외처리() {
        assertThatThrownBy(() -> commentService.write(member, 999L, "commentContent"))
                .isInstanceOf(NoSuchElementException.class);
    }

    @Test
    void 대댓글_쓰기_성공() {
        Long commentId = commentService.write(member, post.getId(), "commentContent");
        Comment comment = commentRepository.findById(commentId).get();
        Long replyId = commentService.addReply(member, post.getId(), comment.getId(), "replyContent");
        Comment reply = commentRepository.findById(replyId).get();

        assertThat(reply.getId()).isEqualTo(replyId);
        assertThat(reply.getParent()).isEqualTo(comment);
        assertThat(comment.getChildren()).containsExactly(reply);
    }

    @Test
    void addReply_시_post없는경우_예외처리() {
        Long commentId = commentService.write(member, post.getId(), "commentContent");
        assertThatThrownBy(() -> commentService.addReply(member, 999L, commentId, "replyContent"))
                .isInstanceOf(NoSuchElementException.class);
    }

    @Test
    void addReply_시_comment없는경우_예외처리() {
        assertThatThrownBy(() -> commentService.addReply(member, post.getId(), 999L, "replyContent"))
                .isInstanceOf(NoSuchElementException.class);
    }
}