package personal.bulletinborad.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import personal.bulletinborad.entity.Post;
import personal.bulletinborad.infrastructure.PostRepository;

@Controller
@RequiredArgsConstructor
@RequestMapping("/posts")
public class PostController {

    private final PostRepository postRepository;

    @GetMapping
    public String posts(Model model) {
        PageRequest pageRequest = PageRequest.of(0, 10);
        Page<Post> posts = postRepository.findAll(pageRequest);
        model.addAttribute("posts", posts);
        return "posts/list";
    }
}
