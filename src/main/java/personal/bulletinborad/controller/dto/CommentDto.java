package personal.bulletinborad.controller.dto;

import lombok.Data;
import personal.bulletinborad.entity.Comment;

@Data
public class CommentDto {
    private String content;
    private String writer;

    public CommentDto(Comment comment) {
        this.content = comment.getContent();
        this.writer = comment.getMember().getNickname();
    }
}
