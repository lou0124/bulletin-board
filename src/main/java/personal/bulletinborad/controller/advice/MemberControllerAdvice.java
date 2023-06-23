package personal.bulletinborad.controller.advice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.MailException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import personal.bulletinborad.controller.MemberController;
import personal.bulletinborad.exception.ExistMemberException;
import personal.bulletinborad.exception.NotMatchedPasswordException;

import static personal.bulletinborad.controller.AttributeNameConst.EXCEPTION_MESSAGE_KEY;

@Slf4j
@ControllerAdvice(assignableTypes = {MemberController.class})
public class MemberControllerAdvice {

    @ExceptionHandler(ExistMemberException.class)
    public String existMemberInfo(ExistMemberException e, RedirectAttributes attributes) {
        attributes.addFlashAttribute(EXCEPTION_MESSAGE_KEY, e.getMessage());
        return "redirect:/members/add";
    }

    @ExceptionHandler(NotMatchedPasswordException.class)
    public String existMemberInfo(NotMatchedPasswordException e, RedirectAttributes attributes) {
        attributes.addFlashAttribute(EXCEPTION_MESSAGE_KEY, e.getMessage());
        return "redirect:/members/add";
    }

    @ExceptionHandler(MailException.class)
    public String a(Exception e, RedirectAttributes attributes) {
        attributes.addFlashAttribute(EXCEPTION_MESSAGE_KEY, "이메일을 보낼 수 없습니다. 이메일을 올바르게 입력하였는지 확인해 주세요.");
        return "redirect:/members/add";
    }
}
