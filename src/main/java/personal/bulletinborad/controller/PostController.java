package personal.bulletinborad.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import personal.bulletinborad.entity.Post;
import personal.bulletinborad.infrastructure.PostRepository;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/posts")
public class PostController {

    private final PostRepository postRepository;

    @GetMapping
    public String posts(@RequestParam(defaultValue = "1") Integer page, Model model) {
        PageRequest pageRequest = PageRequest.of(page - 1, 10);
        Page<Post> posts = postRepository.findAll(pageRequest);
        System.out.println("posts.getNumber() = " + posts.getNumber());
        model.addAttribute("posts", posts);
        return "posts/list";
    }
}
