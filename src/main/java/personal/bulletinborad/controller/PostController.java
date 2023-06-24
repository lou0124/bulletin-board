package personal.bulletinborad.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import personal.bulletinborad.controller.dto.PostDto;
import personal.bulletinborad.controller.dto.PostListDto;
import personal.bulletinborad.entity.Post;
import personal.bulletinborad.infrastructure.PostRepository;

import java.util.Optional;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/posts")
public class PostController {

    private final PostRepository postRepository;

    @GetMapping
    public String posts(@RequestParam(defaultValue = "1") Integer page, Model model) {
        PageRequest pageRequest = PageRequest.of(page - 1, 10);
        Page<PostListDto> posts = postRepository.findAllPost(pageRequest)
                .map(PostListDto::new);
        model.addAttribute("posts", posts);
        return "posts/list";
    }

    @GetMapping("/{postId}")
    public String post(@PathVariable Long postId, Model model) {
        Optional<Post> optionalPost = postRepository.findById(postId);
        Post post = optionalPost.orElse(new Post("없는 게시물 입니다."));
        model.addAttribute("post", new PostDto(post));
        return "posts/post";
    }
}
