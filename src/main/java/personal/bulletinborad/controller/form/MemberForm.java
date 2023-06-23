package personal.bulletinborad.controller.form;

import lombok.Data;

@Data
public class MemberForm {

    private String loginId;
    private String password;
    private String passwordConfirm;
    private String email;
    private String nickname;
    private String verificationCode;
}
