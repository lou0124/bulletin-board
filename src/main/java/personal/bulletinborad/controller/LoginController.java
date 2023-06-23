package personal.bulletinborad.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import personal.bulletinborad.controller.form.LoginForm;
import personal.bulletinborad.entity.Member;
import personal.bulletinborad.service.LoginService;
@Controller
@RequiredArgsConstructor
public class LoginController {

    private final LoginService loginService;

    @GetMapping("/login")
    public String loginForm(@ModelAttribute("loginForm") LoginForm form, @ModelAttribute String message) {
        return "loginForm";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute LoginForm form, RedirectAttributes attributes, HttpServletRequest request) {
        Member member = loginService.login(form.getLoginId(), form.getPassword());

        HttpSession session = request.getSession();
        session.setAttribute("member", member);

        return "redirect:/";
    }

    @PostMapping("/logout")
    public String logoutV3(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        return "redirect:/";
    }
}
