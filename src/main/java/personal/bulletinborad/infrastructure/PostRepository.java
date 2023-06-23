package personal.bulletinborad.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;
import personal.bulletinborad.entity.Post;

public interface PostRepository extends JpaRepository<Post, Long> {
}
