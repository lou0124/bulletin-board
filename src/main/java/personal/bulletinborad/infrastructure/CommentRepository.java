package personal.bulletinborad.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;
import personal.bulletinborad.entity.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
