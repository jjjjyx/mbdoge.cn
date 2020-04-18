package pers.jyx.blog.article;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pers.jyx.blog.Constant;
import pers.jyx.blog.article.model.ArticleDO;
import pers.jyx.blog.article.model.dto.*;
import pers.jyx.blog.user.model.OnlineUserVO;
import pers.jyx.blog.user.model.UserRole;

@Slf4j
@RestController
@RequestMapping(value = Constant.API_SERVLET_URL_PREFIX + "/article")
@PreAuthorize("hasRole('" + UserRole.USER + "')")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    /**
     * 获取文章列表
     *
     * @return
     */
    @GetMapping
    public Page<ArticleDO> getArticles(Pageable pageable, @Validated ArticleQueryCriteriaDTO criteria) {
        return articleService.queryArticle(pageable, criteria);
    }

    /**
     * 查询文章
     *
     * @return
     */
    @GetMapping(value = "/{id}")
    public ArticleDO one(@PathVariable("id") long id) {
        return articleService.findArticleById(id);
    }

    /**
     * 新建
     *
     * @return
     */
    @PostMapping
    public ArticleDO create(@RequestBody @Validated CreateArticleDTO createArticle, Authentication authentication) {
        OnlineUserVO user = (OnlineUserVO) authentication.getPrincipal();

        return articleService.createArticle(user, createArticle);
    }

    /**
     * 删除文章
     *
     * @return
     */
    @DeleteMapping(value = "/{id}")
    public void delete(@PathVariable("id") long id) {
        articleService.deleteArticleById(id);
    }

    /**
     * 更新文章
     *
     * @param id
     * @param updateCategoryDTO
     * @return
     */
    @PutMapping(value = "/{id}")
    public ArticleDO update(@PathVariable("id") long id, @Validated @RequestBody UpdateArticleDTO updateCategoryDTO) {
        return articleService.updateArticleById(id, updateCategoryDTO);
    }

    @PatchMapping(value = "/{id}/content")
    public ArticleDO updateContent(@PathVariable("id") long id, @Validated @RequestBody UpdateArticleContentDTO updateCategoryDTO) {
        return articleService.updateArticleContentById(id, updateCategoryDTO);
    }

    @PatchMapping(value = "/{id}/status")
    public ArticleDO updateStatus(@PathVariable("id") long id, @Validated @RequestBody UpdateArticleStatusDTO updateCategoryDTO) {
        return articleService.updateArticleStatusById(id, updateCategoryDTO);
    }

    @PatchMapping(value = "/{id}/tags")
    public ArticleDO updateStatus(@PathVariable("id") long id, @Validated @RequestBody UpdateArticleTagsDTO updateCategoryDTO) {
        return articleService.updateArticleTagsById(id, updateCategoryDTO);
    }
}
