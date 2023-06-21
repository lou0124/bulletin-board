package personal.bulletinborad.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.NoArgsConstructor;
import personal.bulletinborad.enumtype.Approval;
import personal.bulletinborad.enumtype.Role;

import static lombok.AccessLevel.*;

@Entity
@NoArgsConstructor(access = PROTECTED)
public class User {

    @Id @GeneratedValue
    @Column(name = "user_id")
    private Long id;

    private String loginId;
    private String password;
    private String email;
    private String nickname;
    private Approval approval;
    private Role role;
}
