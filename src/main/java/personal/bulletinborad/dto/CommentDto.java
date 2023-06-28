package personal.bulletinborad.dto;

import lombok.Data;
import personal.bulletinborad.entity.Comment;

import java.util.List;

@Data
public class CommentDto {
    private Long id;
    private String content;
    private String writer;
    private List<CommentDto> replies;

    public static CommentDto createCommentDto(Comment comment) {
        CommentDto commentDto = createBasicDto(comment);
        commentDto.setReplies(
                comment.getChildren()
                .stream()
                .map(CommentDto::createBasicDto)
                .toList()
        );
        return commentDto;
    }

    private static CommentDto createBasicDto(Comment comment) {
        CommentDto commentDto = new CommentDto();
        commentDto.setId(comment.getId());
        commentDto.setContent(comment.getContent());
        commentDto.setWriter(comment.getMember().getNickname());
        return commentDto;
    }

}
