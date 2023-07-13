package personal.bulletinborad.entity;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;
import personal.bulletinborad.entity.mapedsuperclass.BaseTime;

import java.util.ArrayList;
import java.util.List;

import static lombok.AccessLevel.*;

@Entity
@NoArgsConstructor(access = PROTECTED)
public class Category extends BaseTime {

    @Id @GeneratedValue
    @Column(name = "category_id")
    private Long id;

    @Column(nullable = false)
    private String name;

    @OneToMany(mappedBy = "category", orphanRemoval = true)
    private List<Post> posts = new ArrayList<>();
}
