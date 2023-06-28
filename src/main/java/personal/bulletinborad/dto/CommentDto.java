package personal.bulletinborad.dto;

import lombok.Data;
import personal.bulletinborad.entity.Comment;

@Data
public class CommentDto {
    private Long id;
    private String content;
    private String writer;

    public CommentDto(Comment comment) {
        this.id = comment.getId();
        this.content = comment.getContent();
        this.writer = comment.getMember().getNickname();
    }
}
