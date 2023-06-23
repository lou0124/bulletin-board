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
        LocalDateTime date = post.getCreatedDate();
        String formattedDate = String.format("%d-%02d-%02d %02d:%02d:%02d",
                date.getYear(), date.getMonthValue(), date.getDayOfMonth(),
                date.getHour(), date.getMinute(), date.getSecond());
        this.writtenDate = formattedDate;
    }

    public PostListDto(Long id, String title, String writer, LocalDateTime date) {
        this.id = id;
        this.title = title;
        this.writer = writer;
        String formattedDate = String.format("%d-%02d-%02d %02d:%02d:%02d",
                date.getYear(), date.getMonthValue(), date.getDayOfMonth(),
                date.getHour(), date.getMinute(), date.getSecond());
        this.writtenDate = formattedDate;
    }
}
