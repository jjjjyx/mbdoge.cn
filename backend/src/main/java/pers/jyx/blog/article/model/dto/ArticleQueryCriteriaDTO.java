package pers.jyx.blog.article.model.dto;

import lombok.Getter;
import lombok.Setter;
import pers.jyx.blog.article.model.ArticleDO;

@Getter
@Setter
public class ArticleQueryCriteriaDTO {
    // 标题
    private String keyword;
    private ArticleDO.Type type;
    private ArticleDO.Status status;
    private String category;

}
