package personal.bulletinborad.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import personal.bulletinborad.entity.Comment;
import personal.bulletinborad.entity.Member;
import personal.bulletinborad.entity.Post;
import personal.bulletinborad.infrastructure.CommentRepository;
import personal.bulletinborad.infrastructure.PostRepository;

import java.util.NoSuchElementException;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentService {

    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    @Transactional
    public Long write(Member member, Long postId, String content) {
        Optional<Post> optionalPost = postRepository.findById(postId);
        Post post = optionalPost.orElseThrow(NoSuchElementException::new);
        Comment comment = commentRepository.save(new Comment(content, member, post));
        return comment.getId();
    }

    @Transactional
    public Long addReply(Member member, Long postId, Long commentId, String content) {
        Optional<Post> optionalPost = postRepository.findById(postId);
        Post post = optionalPost.orElseThrow(NoSuchElementException::new);

        Optional<Comment> optionalComment = commentRepository.findById(commentId);
        Comment comment = optionalComment.orElseThrow(NoSuchElementException::new);

        Comment reply = new Comment(content, member, post);
        comment.addReply(reply);
        commentRepository.save(reply);

        return reply.getId();
    }
}
