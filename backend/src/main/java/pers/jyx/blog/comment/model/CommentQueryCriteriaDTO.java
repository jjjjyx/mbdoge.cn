package pers.jyx.blog.comment.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentQueryCriteriaDTO {
    private String target;
    private String keyword;
    private String author;
}
