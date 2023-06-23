package personal.bulletinborad.controller.advice;

import jakarta.mail.MessagingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.MailException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import personal.bulletinborad.controller.MemberController;
import personal.bulletinborad.exception.ExistMemberException;

import java.io.UnsupportedEncodingException;

@Slf4j
@ControllerAdvice(assignableTypes = {MemberController.class})
public class MemberControllerAdvice {

    private final String EXCEPTION_MESSAGE_KEY = "message";

    @ExceptionHandler(ExistMemberException.class)
    public String existMemberInfo(ExistMemberException e, RedirectAttributes attributes) {
        attributes.addFlashAttribute(EXCEPTION_MESSAGE_KEY, e.getMessage());
        return "redirect:/members/add";
    }

    @ExceptionHandler(MailException.class)
    public String a(Exception e, RedirectAttributes attributes) {
        attributes.addFlashAttribute(EXCEPTION_MESSAGE_KEY, "이메일을 보낼 수 없습니다. 이메일을 올바르게 입력하였는지 확인해 주세요.");
        return "redirect:/members/add";
    }
}
