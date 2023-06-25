package personal.bulletinborad.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import personal.bulletinborad.controller.form.MemberForm;
import personal.bulletinborad.exception.NotMatchedPasswordException;
import personal.bulletinborad.service.MemberService;

import static personal.bulletinborad.controller.AttributeNameConst.EXCEPTION_MESSAGE_KEY;
import static personal.bulletinborad.controller.AttributeNameConst.MEMBER_FORM_KEY;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/add")
    public String addForm(@ModelAttribute(MEMBER_FORM_KEY) MemberForm memberForm,
                          @ModelAttribute(EXCEPTION_MESSAGE_KEY) String message) {
        return "members/addMemberForm";
    }

    @PostMapping("/add")
    public String join(@ModelAttribute MemberForm form) {

        if (!form.getPassword().equals(form.getPasswordConfirm())) {
            throw new NotMatchedPasswordException("패스워드 확인 실패");
        }

        Long memberId = memberService.join(form.getLoginId(), form.getPassword(), form.getEmail(), form.getNickname());
        return "redirect:/members/verify/" + memberId;
    }

    @GetMapping("/verify/{memberId}")
    public String verifyForm(@PathVariable String memberId) {
        return "members/verifyMemberForm";
    }

    @PostMapping("/verify/{memberId}")
    public String verify(@PathVariable Long memberId, @ModelAttribute("code") String code, RedirectAttributes attributes) {
        if (memberService.verify(memberId, code)) {
            log.info("memberId = {} code = {} 인증 성공", memberId);
            return "redirect:/";
        }

        attributes.addFlashAttribute(EXCEPTION_MESSAGE_KEY, "인증에 실패 하였습니다.");
        return "redirect:/members/verify/" + memberId;
    }
}
