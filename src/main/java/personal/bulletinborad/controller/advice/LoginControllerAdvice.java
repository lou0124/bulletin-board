package personal.bulletinborad.controller.advice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import personal.bulletinborad.controller.LoginController;
import personal.bulletinborad.exception.LoginException;

import static personal.bulletinborad.controller.AttributeNameConst.EXCEPTION_MESSAGE_KEY;

@Slf4j
@ControllerAdvice(assignableTypes = {LoginController.class})
public class LoginControllerAdvice {

    @ExceptionHandler(LoginException.class)
    public String existMemberInfo(LoginException e, RedirectAttributes attributes) {
        attributes.addFlashAttribute(EXCEPTION_MESSAGE_KEY, e.getMessage());
        return "redirect:/login";
    }
}
