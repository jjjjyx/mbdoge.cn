package pers.jyx.blog.article;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pers.jyx.blog.article.model.ArticleQueryCriteriaDTO;
import pers.jyx.blog.article.model.ArticleVO;
import pers.jyx.blog.article.model.CreateArticleDTO;

@Slf4j
@Service
public class ArticleService {

    /**
     * 查询文章列表
     * @param pageable
     * @param criteria
     * @return
     */
    public Page<ArticleVO> queryArticle (Pageable pageable, ArticleQueryCriteriaDTO criteria) {
        return null;
    }

    public ArticleVO findArticleById(long id) {
        return null;
    }

    public ArticleVO createArticle(CreateArticleDTO createArticle) {
        return null;
    }

    public void deleteArticleById(long id) {

    }
}
