package pers.jyx.blog.comment.model;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;

@Getter
@Setter
public class CommentQueryCriteriaDTO {
    private String target;
    private String keyword;
    private String author;
    private CommentDO.Status status;
}
