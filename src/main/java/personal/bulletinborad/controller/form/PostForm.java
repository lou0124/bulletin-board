package personal.bulletinborad.controller.form;

import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class PostForm {

    @Size(max = 50, message = "제목은 50자 이내입니다.")
    private String title;

    @Size(max = 500, message = "내용은 500자 이내입니다.")
    private String content;
}
