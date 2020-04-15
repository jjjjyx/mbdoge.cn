package pers.jyx.blog.article.model.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class UpdateArticleContentDTO {

    @NotNull
    private String content;
}
