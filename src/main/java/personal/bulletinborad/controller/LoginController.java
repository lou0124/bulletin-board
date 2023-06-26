package personal.bulletinborad.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import personal.bulletinborad.controller.form.LoginForm;
import personal.bulletinborad.entity.Member;
import personal.bulletinborad.service.LoginService;

import static personal.bulletinborad.controller.AttributeNameConst.*;

@Slf4j
@Controller
@RequiredArgsConstructor
public class LoginController {

    private final LoginService loginService;

    @GetMapping("/login")
    public String loginForm(@ModelAttribute(LOGIN_FORM_KEY) LoginForm loginForm,
                            @ModelAttribute(EXCEPTION_MESSAGE_KEY) String message) {
        return "login/loginForm";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute LoginForm form,
                        @RequestParam(defaultValue = "/") String redirectPath,
                        HttpServletRequest request) {
        Member member = loginService.login(form.getLoginId(), form.getPassword());

        HttpSession session = request.getSession();
        session.setAttribute(SESSION_MEMBER, member);
        return "redirect:" + redirectPath;
    }

    @GetMapping("/logout")
    public String logout(@RequestParam(defaultValue = "/") String redirectPath, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        return "redirect:" + redirectPath;
    }
}
