package personal.bulletinborad.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import personal.bulletinborad.dto.CommentDto;
import personal.bulletinborad.dto.PostDto;
import personal.bulletinborad.dto.PostListDto;
import personal.bulletinborad.entity.Member;
import personal.bulletinborad.entity.Post;
import personal.bulletinborad.infrastructure.CommentRepository;
import personal.bulletinborad.infrastructure.PostRepository;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostService {

    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    public Page<PostListDto> getPosts(Integer page) {
        PageRequest pageRequest = PageRequest.of(page - 1, 10);
        return postRepository.findAllPost(pageRequest)
                .map(PostListDto::new);
    }

    public PostDto getPost(Integer page, Long postId) {
        Optional<Post> optionalPost = postRepository.findById(postId);
        Post post = optionalPost.orElseThrow(NoSuchElementException::new);

        PageRequest pageRequest = PageRequest.of( page - 1, 10);
        Page<CommentDto> comments = commentRepository.findByPost(pageRequest, post)
                .map(CommentDto::new);
        return new PostDto(post, comments);
    }

    @Transactional
    public Long write(Member member, String title, String content) {
        Post post = new Post(member, null, title, content, 0, null);
        postRepository.save(post);
        return post.getId();
    }
}
