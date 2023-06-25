package personal.bulletinborad.controller.form;

import lombok.Data;

@Data
public class LoginForm {

    private String loginId;
    private String password;

    public LoginForm(String loginId) {
        this.loginId = loginId;
        this.password = null;
    }
}
