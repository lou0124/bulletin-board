package personal.bulletinborad.controller.form;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class LoginForm {

    @NotEmpty(message = "아이디는 필수 항목입니다.")
    private String loginId;

    @NotEmpty(message = "비밀번호는 필수 항목입니다.")
    private String password;

    public LoginForm(String loginId) {
        this.loginId = loginId;
        this.password = null;
    }
}
