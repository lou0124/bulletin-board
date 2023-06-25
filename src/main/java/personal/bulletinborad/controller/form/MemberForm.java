package personal.bulletinborad.controller.form;

import lombok.Data;

@Data
public class MemberForm {

    private String loginId;
    private String password;
    private String passwordConfirm;
    private String email;
    private String nickname;

    public MemberForm(String loginId, String email, String nickname) {
        this.loginId = loginId;
        this.email = email;
        this.nickname = nickname;
    }
}
