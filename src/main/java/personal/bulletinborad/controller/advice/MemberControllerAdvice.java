package personal.bulletinborad.controller.advice;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.MailException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import personal.bulletinborad.controller.MemberController;
import personal.bulletinborad.controller.form.MemberForm;
import personal.bulletinborad.exception.ExistMemberException;
import personal.bulletinborad.exception.NotMatchedPasswordException;

import java.util.Enumeration;

import static personal.bulletinborad.controller.AttributeNameConst.EXCEPTION_MESSAGE_KEY;
import static personal.bulletinborad.controller.AttributeNameConst.MEMBER_FORM_KEY;
import static personal.bulletinborad.exception.ExceptionMessage.*;

@Slf4j
@ControllerAdvice(assignableTypes = {MemberController.class})
public class MemberControllerAdvice {

    @ExceptionHandler(ExistMemberException.class)
    public String existMemberInfo(ExistMemberException e, RedirectAttributes attributes, HttpServletRequest request) {

        MemberForm memberForm = createMemberForm(request);

        if (e.getMessage().equals(EXIST_LOGIN_ID)) {
            memberForm.setLoginId(null);
        } else if (e.getMessage().equals(EXIST_NICKNAME)) {
            memberForm.setNickname(null);
        }

        attributes.addFlashAttribute(MEMBER_FORM_KEY, memberForm);
        attributes.addFlashAttribute(EXCEPTION_MESSAGE_KEY, e.getMessage());
        return "redirect:/members/add";
    }

    @ExceptionHandler(NotMatchedPasswordException.class)
    public String existMemberInfo(NotMatchedPasswordException e, RedirectAttributes attributes, HttpServletRequest request) {

        MemberForm memberForm = createMemberForm(request);
        memberForm.setPassword(null);
        memberForm.setPasswordConfirm(null);

        attributes.addFlashAttribute(MEMBER_FORM_KEY, memberForm);
        attributes.addFlashAttribute(EXCEPTION_MESSAGE_KEY, e.getMessage());
        return "redirect:/members/add";
    }

    @ExceptionHandler(MailException.class)
    public String failSendMail(MailException e, RedirectAttributes attributes, HttpServletRequest request) {
        MemberForm memberForm = createMemberForm(request);
        memberForm.setEmail(null);
        attributes.addFlashAttribute(EXCEPTION_MESSAGE_KEY, e.getMessage());
        return "redirect:/members/add";
    }

    private MemberForm createMemberForm(HttpServletRequest request) {
        String loginId = request.getParameter("loginId");
        String password = request.getParameter("password");
        String passwordConfirm = request.getParameter("passwordConfirm");
        String email = request.getParameter("email");
        String nickname = request.getParameter("nickname");
        MemberForm memberForm = new MemberForm(loginId, password, passwordConfirm, email, nickname);
        return memberForm;
    }
}
