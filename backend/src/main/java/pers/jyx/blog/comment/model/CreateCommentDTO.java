package pers.jyx.blog.comment.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Null;

@Getter
@Setter
public class CreateCommentDTO {
    @NotBlank
    private String target;

    @NotBlank
    @Length(min = 1, max = 280)
    private String content;

    private Long parent;
}
