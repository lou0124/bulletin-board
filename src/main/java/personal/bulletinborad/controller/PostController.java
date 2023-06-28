package personal.bulletinborad.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import personal.bulletinborad.controller.dto.CommentDto;
import personal.bulletinborad.controller.dto.PostDto;
import personal.bulletinborad.controller.dto.PostListDto;
import personal.bulletinborad.controller.form.PostForm;
import personal.bulletinborad.entity.Member;
import personal.bulletinborad.entity.Post;
import personal.bulletinborad.infrastructure.CommentRepository;
import personal.bulletinborad.infrastructure.PostRepository;

import java.util.NoSuchElementException;
import java.util.Optional;

import static personal.bulletinborad.controller.Util.LoginMemberGetter.getLoginMember;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/posts")
public class PostController {

    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    @GetMapping
    public String posts(@RequestParam(defaultValue = "1") Integer page, Model model, HttpServletRequest request) {
        Member member = (Member) getLoginMember(request);
        PageRequest pageRequest = PageRequest.of(page - 1, 10);
        Page<PostListDto> posts = postRepository.findAllPost(pageRequest)
                .map(PostListDto::new);
        model.addAttribute("nickname", getNickname(member));
        model.addAttribute("posts", posts);
        return "posts/list";
    }

    @GetMapping("/{postId}")
    public String post(@RequestParam(defaultValue = "1") Integer page,
                       @PathVariable Long postId,
                       Model model,
                       HttpServletRequest request) {
        Member member = (Member) getLoginMember(request);

        Optional<Post> optionalPost = postRepository.findById(postId);
        Post post = optionalPost.orElseThrow(NoSuchElementException::new);

        PageRequest pageRequest = PageRequest.of( page - 1, 10);
        Page<CommentDto> comments = commentRepository.findByPost(pageRequest, post).map(CommentDto::new);
        PostDto postDto = new PostDto(post, comments);

        model.addAttribute("nickname", getNickname(member));
        model.addAttribute("post", postDto);
        return "posts/post";
    }

    @GetMapping("/add")
    public String addForm(@ModelAttribute PostForm postForm, HttpServletRequest request) {
        Member member = (Member) getLoginMember(request);
        if (member == null) {
            return "redirect:/login";
        };
        return "posts/postForm";
    }

    @PostMapping("/add")
    public String write(@ModelAttribute PostForm form, HttpServletRequest request) {
        Member member = (Member) getLoginMember(request);
        if (member == null) {
            return "redirect:/login";
        }

        Post post = new Post(member, null, form.getTitle(), form.getContent(), 0, null);
        postRepository.save(post);

        return "redirect:/posts";
    }

    private String getNickname(Member member) {
        return member != null ? member.getNickname() : null;
    }
}
