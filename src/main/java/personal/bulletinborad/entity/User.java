package personal.bulletinborad.entity;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;
import personal.bulletinborad.entity.mapedsuperclass.BaseTime;
import personal.bulletinborad.enumtype.Approval;
import personal.bulletinborad.enumtype.Role;

import static jakarta.persistence.EnumType.*;
import static lombok.AccessLevel.*;

@Entity
@NoArgsConstructor(access = PROTECTED)
public class User extends BaseTime {

    @Id @GeneratedValue
    @Column(name = "user_id")
    private Long id;

    @Column(nullable = false)
    private String loginId;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String nickname;

    @Enumerated(STRING)
    @Column(nullable = false)
    private Approval approval;

    @Enumerated(STRING)
    @Column(nullable = false)
    private Role role;
}
