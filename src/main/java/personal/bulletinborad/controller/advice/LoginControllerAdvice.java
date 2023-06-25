package personal.bulletinborad.controller.advice;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import personal.bulletinborad.controller.LoginController;
import personal.bulletinborad.controller.form.LoginForm;
import personal.bulletinborad.exception.LoginException;
import personal.bulletinborad.exception.NotMatchedPasswordException;

import static personal.bulletinborad.controller.AttributeNameConst.EXCEPTION_MESSAGE_KEY;

@Slf4j
@ControllerAdvice(assignableTypes = {LoginController.class})
public class LoginControllerAdvice {

    @ExceptionHandler({LoginException.class, NotMatchedPasswordException.class})
    public String existMemberInfo(Exception e, RedirectAttributes attributes, HttpServletRequest request) {

        attributes.addFlashAttribute(EXCEPTION_MESSAGE_KEY, e.getMessage());
        if (e instanceof NotMatchedPasswordException) {
            LoginForm loginForm = new LoginForm(request.getParameter("loginId"));
            attributes.addFlashAttribute("loginForm", loginForm);
        }

        return "redirect:/login";
    }
}
