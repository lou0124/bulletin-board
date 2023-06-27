package personal.bulletinborad.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import personal.bulletinborad.entity.Member;
import personal.bulletinborad.service.CommentService;

import static personal.bulletinborad.controller.Util.LoginMemberGetter.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/comments")
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/add")
    public String write(@RequestParam Long postId, @RequestParam String content, HttpServletRequest request) {

        Member member = (Member) getLoginMember(request);
        if (member == null) {
            return "redirect:/login?redirectPath=/posts/" + postId;
        }

        commentService.write(member, postId, content);
        return "redirect:/posts/" + postId + "?page=1";
    }

    @PostMapping("/addReply")
    public String addReply() {
        return "posts/list";
    }
}
