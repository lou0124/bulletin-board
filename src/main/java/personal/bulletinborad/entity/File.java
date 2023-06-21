package personal.bulletinborad.entity;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;
import personal.bulletinborad.entity.mapedsuperclass.BaseTime;

import static jakarta.persistence.FetchType.*;
import static lombok.AccessLevel.*;

@Entity
@NoArgsConstructor(access = PROTECTED)
public class File extends BaseTime {

    @Id @GeneratedValue
    @Column(name = "file_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    @Column(nullable = false)
    private String uploadName;

    @Column(nullable = false)
    private String storedName;
}
