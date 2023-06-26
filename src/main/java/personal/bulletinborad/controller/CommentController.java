package personal.bulletinborad.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import personal.bulletinborad.controller.Util.LoginMemberGetter;
import personal.bulletinborad.entity.Member;

import static personal.bulletinborad.controller.Util.LoginMemberGetter.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/comments")
public class CommentController {

    @PostMapping("/add")
    public String write(@RequestParam Long postId, @RequestParam String content, HttpServletRequest request) {

        Member member = (Member) getLoginMember(request);
        if (member == null) {
            return "redirect:/login?redirectPath=/posts/" + postId;
        }

        return "redirect:/posts/" + postId;
    }
}
