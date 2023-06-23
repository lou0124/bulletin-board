package personal.bulletinborad.infrastructure;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import personal.bulletinborad.entity.Post;


public interface PostRepository extends JpaRepository<Post, Long> {

    @Query("SELECT p FROM Post p join fetch p.member m order by p.createdDate desc")
    Page<Post> findAllPost(Pageable pageable);
}
