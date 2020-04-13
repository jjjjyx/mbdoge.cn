package pers.jyx.blog.article;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pers.jyx.blog.Constant;
import pers.jyx.blog.article.model.ArticleQueryCriteriaDTO;
import pers.jyx.blog.article.model.ArticleVO;
import pers.jyx.blog.article.model.CreateArticleDTO;
import pers.jyx.blog.user.model.UserRole;

@Slf4j
@RestController
@RequestMapping(value = Constant.API_SERVLET_URL_PREFIX+ "/article")
@PreAuthorize("hasRole('"+ UserRole.USER +"')")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    /**
     * 获取文章列表
     * @return
     */
    @GetMapping
    public Page<ArticleVO> getArticles (Pageable pageable, ArticleQueryCriteriaDTO criteria) {
        return articleService.queryArticle(pageable, criteria);
    }

    /**
     * 查询文章
     * @return
     */
    @GetMapping(value = "/{id}")
    public ArticleVO one (@PathVariable("id") long id) {
        return articleService.findArticleById(id);
    }

    /**
     * 新建
     * @return
     */
    @PostMapping
    public ArticleVO create (@Validated CreateArticleDTO createArticle) {
        return articleService.createArticle(createArticle);
    }

    /**
     * 删除文章
     * @return
     */
    @DeleteMapping(value = "/{id}")
    public void delete (@PathVariable("id") long id) {
        articleService.deleteArticleById(id);
    }

    // todo 更新文章
}
