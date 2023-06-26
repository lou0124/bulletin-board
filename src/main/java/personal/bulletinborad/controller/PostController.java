package personal.bulletinborad.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import personal.bulletinborad.controller.dto.PostDto;
import personal.bulletinborad.controller.dto.PostListDto;
import personal.bulletinborad.controller.form.PostForm;
import personal.bulletinborad.entity.Member;
import personal.bulletinborad.entity.Post;
import personal.bulletinborad.infrastructure.PostRepository;

import java.util.Optional;

import static personal.bulletinborad.controller.AttributeNameConst.SESSION_MEMBER;
import static personal.bulletinborad.controller.Util.LoginMemberGetter.getLoginMember;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/posts")
public class PostController {

    private final PostRepository postRepository;

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
    public String post(@PathVariable Long postId, Model model) {
        Optional<Post> optionalPost = postRepository.findById(postId);
        Post post = optionalPost.orElse(new Post("없는 게시물 입니다."));
        model.addAttribute("post", new PostDto(post));
        return "posts/post";
    }

    @GetMapping("/add")
    public String addForm(@ModelAttribute PostForm postForm, HttpServletRequest request, Model model) {
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

//    private Member getLoginMember(HttpServletRequest request) {
//        HttpSession session = request.getSession(false);
//        if (session == null) {
//            return null;
//        }
//
//        return (Member) session.getAttribute(SESSION_MEMBER);
//    }

    private String getNickname(Member member) {
        return member != null ? member.getNickname() : null;
    }
}
