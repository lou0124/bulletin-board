package personal.bulletinborad.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;
import personal.bulletinborad.dto.PostDto;
import personal.bulletinborad.dto.PostListDto;
import personal.bulletinborad.entity.Comment;
import personal.bulletinborad.entity.Member;
import personal.bulletinborad.entity.Post;
import personal.bulletinborad.infrastructure.CommentRepository;
import personal.bulletinborad.infrastructure.MemberRepository;
import personal.bulletinborad.infrastructure.PostRepository;

import static org.assertj.core.api.Assertions.*;

/***
 *
 * 임시 작성
 */

@SpringBootTest
@Transactional
class PostServiceTest {


    @Autowired PostService postService;
    @Autowired CommentService commentService;
    @Autowired MemberRepository memberRepository;
    @Autowired PostRepository postRepository;
    @Autowired CommentRepository commentRepository;

    private final Long LAST_POST_NUM = 200L;
    private final Long COMMENTS_SIZE = 20L;
    Member member1 = new Member("test", "1234", "abc@naver.com", "apple");
    Member member2 = new Member("test2", "1234", "abc2@naver.com", "banana");

    @BeforeEach
    void beforeEach() {

        member1.verify();
        member2.verify();
        memberRepository.save(member1);
        memberRepository.save(member2);

        for (int i = 1; i <= LAST_POST_NUM; i++) {
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
        for (int j = 1; j <= COMMENTS_SIZE; j++) {
            if (j % 2 == 0) {
                commentRepository.save(new Comment("content" + j, member1, post));
            } else {
                commentRepository.save(new Comment("content" + j, member2, post));
            }
        }
    }

    @Test
    void 게시글_목록_가져오기() {
        Page<PostListDto> posts = postService.getPosts(1);
        assertThat(posts.getSize()).isEqualTo(10);
        assertThat(posts.getContent().get(0).getId()).isEqualTo(LAST_POST_NUM);
        assertThat(posts.getContent().get(9).getId()).isEqualTo(LAST_POST_NUM - 9);
        assertThatThrownBy(() -> posts.getContent().get(10).getId())
                .isInstanceOf(IndexOutOfBoundsException.class);
    }

    @Test
    void 게시글_하나_가져오기() {

        Long postId = postService.write(member1, "postTitle", "postContent");
        Long commentId1 = commentService.write(member1, postId, "commentContent1");
        Long commentId2 = commentService.write(member1, postId, "commentContent2");

        for (int i = 1; i <= 5; i++) {
            commentService.addReply(member1, postId, commentId2, "replyContent" + i);
        }

        PostDto post = postService.getPost(1, postId);

        assertThat(post.getId()).isEqualTo(postId);
        assertThat(post.getComments().getContent().size()).isEqualTo(2);
        assertThat(post.getComments().getContent().get(0).getReplies().size()).isEqualTo(5);
    }
}