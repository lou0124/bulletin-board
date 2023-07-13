package personal.bulletinborad.dto;

import lombok.Data;
import org.springframework.data.domain.Page;
import personal.bulletinborad.entity.Post;

@Data
public class PostDto {

    private Long id;
    private String title;
    private String content;
    private String writer;
    private String writtenDate;
    private Page<CommentDto> comments;

    public PostDto(Post post, Page<CommentDto> comments) {
        this.id = post.getId();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.writer = post.getMember().getNickname();
        this.writtenDate = post.formattedDate();
        this.comments = comments;
    }
}
