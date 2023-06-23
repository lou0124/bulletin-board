package personal.bulletinborad.controller;

import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import personal.bulletinborad.controller.form.MemberForm;
import personal.bulletinborad.exception.LoginException;
import personal.bulletinborad.exception.NotMatchedPasswordException;
import personal.bulletinborad.service.MemberService;

import java.io.UnsupportedEncodingException;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/add")
    public String addForm(@ModelAttribute("memberForm") MemberForm form, @ModelAttribute String message) {
        return "members/addMemberForm";
    }

    @PostMapping("/add")
    public String join(@ModelAttribute("memberForm") MemberForm form, Model model) {

        if (!form.getPassword().equals(form.getPasswordConfirm())) {
            throw new NotMatchedPasswordException("패스워드 확인 실패");
        }

        memberService.join(form.getLoginId(), form.getPassword(), form.getEmail(), form.getNickname());
        return "redirect:/";
    }
}
