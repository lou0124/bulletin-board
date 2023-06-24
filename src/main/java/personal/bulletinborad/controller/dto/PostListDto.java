package personal.bulletinborad.controller.dto;

import lombok.Data;
import personal.bulletinborad.entity.Post;

import java.time.LocalDateTime;

@Data
public class PostListDto {

    private Long id;
    private String title;
    private String writer;
    private String writtenDate;

    public PostListDto(Post post) {
        this.id = post.getId();
        this.title = post.getTitle();
        this.writer = post.getMember().getNickname();
        this.writtenDate = post.formattedDate();
    }
}
