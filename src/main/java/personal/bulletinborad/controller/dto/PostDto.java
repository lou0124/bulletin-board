package personal.bulletinborad.controller.dto;

import lombok.Data;
import personal.bulletinborad.entity.Post;

import java.util.List;

@Data
public class PostDto {

    private Long id;
    private String title;
    private String content;
    private String writer;
    private String writtenDate;
    public List<CommentDto> comments;

    public PostDto(Post post) {
        this.id = post.getId();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.writer = post.getMember().getNickname();
        this.writtenDate = post.formattedDate();
        this.comments = post.getComments()
                .stream()
                .map(CommentDto::new)
                .toList();
    }
}
