package personal.bulletinborad.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import personal.bulletinborad.entity.Comment;
import personal.bulletinborad.entity.Member;
import personal.bulletinborad.entity.Post;
import personal.bulletinborad.infrastructure.CommentRepository;
import personal.bulletinborad.infrastructure.PostRepository;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    public Long write(Member member, Long postId, String content) {
        Optional<Post> optionalPost = postRepository.findById(postId);
        Post post = optionalPost.orElseThrow(NoSuchElementException::new);
        Comment comment = commentRepository.save(new Comment(content, member, post));
        return comment.getId();
    }
}
