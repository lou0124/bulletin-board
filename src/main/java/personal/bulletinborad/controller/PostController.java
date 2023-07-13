package personal.bulletinborad.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import personal.bulletinborad.dto.PostDto;
import personal.bulletinborad.dto.PostListDto;
import personal.bulletinborad.controller.form.PostForm;
import personal.bulletinborad.entity.Member;
import personal.bulletinborad.service.PostService;

import static personal.bulletinborad.controller.util.LoginMemberGetter.getLoginMember;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;

    @GetMapping
    public String posts(@RequestParam(defaultValue = "1") Integer page, Model model, HttpServletRequest request) {

        Member member = (Member) getLoginMember(request);
        Page<PostListDto> posts = postService.getPosts(page);

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
        PostDto post = postService.getPost(page, postId);

        model.addAttribute("nickname", getNickname(member));
        model.addAttribute("post", post);
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
    public String write(@Valid @ModelAttribute PostForm form,
                        BindingResult bindingResult,
                        HttpServletRequest request) {

        Member member = (Member) getLoginMember(request);
        if (member == null) {
            return "redirect:/login";
        }

        if (bindingResult.hasErrors()) {
            return "posts/postForm";
        }

        postService.write(member, form.getTitle(), form.getContent());
        return "redirect:/posts";
    }

    private String getNickname(Member member) {
        return member != null ? member.getNickname() : null;
    }
}
