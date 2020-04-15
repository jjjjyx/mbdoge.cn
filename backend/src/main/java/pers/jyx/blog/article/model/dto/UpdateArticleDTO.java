package pers.jyx.blog.article.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateArticleDTO extends CreateArticleDTO {
    private String alias;
    private ArticleDO.Status status;
    private int seqInManage;
    private boolean fixedTop;
}
