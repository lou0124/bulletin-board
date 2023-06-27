package personal.bulletinborad.infrastructure;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import personal.bulletinborad.entity.Comment;
import personal.bulletinborad.entity.Post;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Query(value = "select c from Comment c join fetch c.member m where c.post = :post order by c.createdDate desc ",
            countQuery = "select count(c) from Comment c")
    Page<Comment> findByPost(Pageable pageable, @Param("post") Post post);
}
