package pers.jyx.blog.article.model.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class UpdateArticleDTO extends CreateArticleDTO {
    private String alias;
    @NotNull
    private ArticleDO.Status status;
    private int seqInManage;
    private boolean fixedTop;
}
