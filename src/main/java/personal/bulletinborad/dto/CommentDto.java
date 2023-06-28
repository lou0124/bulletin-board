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

    private CommentDto() {
    }

//    public CommentDto(Comment comment) {
//        this.id = comment.getId();
//        this.content = comment.getContent();
//        this.writer = comment.getMember().getNickname();
//        comment.getChildren().stream().map(c -> {
//            CommentDto commentDto = new CommentDto();
//            commentDto.setId(c.getId());
//            commentDto.setContent(c.getContent());
//            commentDto.setWriter(c.getMember().getNickname());
//            return commentDto;
//        });
//    }

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
