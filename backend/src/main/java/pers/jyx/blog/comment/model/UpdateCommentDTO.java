package pers.jyx.blog.comment.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class UpdateCommentDTO {

    @NotNull
    private CommentDO.Status status;
}
