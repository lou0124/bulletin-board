package personal.bulletinborad.infrastructure;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import personal.bulletinborad.entity.Post;

import java.util.Optional;


public interface PostRepository extends JpaRepository<Post, Long> {

    @Query("select p from Post p join fetch p.member m order by p.createdDate desc")
    Page<Post> findAllPost(Pageable pageable);
}
