package personal.bulletinborad.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import personal.bulletinborad.entity.mapedsuperclass.BaseTime;
import personal.bulletinborad.enumtype.Verification;
import personal.bulletinborad.enumtype.Role;

import static jakarta.persistence.EnumType.*;
import static lombok.AccessLevel.*;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
public class Member extends BaseTime {

    @Id @GeneratedValue
    @Column(name = "user_id")
    private Long id;

    @Column(nullable = false, unique = true)
    private String loginId;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false, unique = true)
    private String nickname;

    @Enumerated(STRING)
    @Column(nullable = false)
    private Verification verification;

    @Enumerated(STRING)
    @Column(nullable = false)
    private Role role;

    public Member(String loginId, String password, String email, String nickname) {
        this.loginId = loginId;
        this.password = password;
        this.email = email;
        this.nickname = nickname;
        this.verification = Verification.NONE;
        this.role = Role.COMMON;
    }

    public void verify() {
        verification = Verification.COMPLETE;
    }

    public boolean isVerified() {
        return getVerification() == Verification.COMPLETE ? true : false;
    }

    public boolean isMatchedPassword(String password) {
        return getPassword().equals(password);
    }
}
