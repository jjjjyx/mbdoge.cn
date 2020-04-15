package pers.jyx.blog.article;

import cn.mbdoge.jyx.exception.LocalServiceException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import pers.jyx.blog.MiscProperties;
import pers.jyx.blog.article.model.*;
import pers.jyx.blog.article.model.dto.*;
import pers.jyx.blog.article.repository.ArticleRepository;
import pers.jyx.blog.article.repository.CategoryRepository;
import pers.jyx.blog.basic.SnowFlake;
import pers.jyx.blog.user.model.OnlineUserVO;
import pers.jyx.blog.user.model.UserDO;
import pers.jyx.blog.user.model.UserRepository;

import javax.persistence.criteria.*;
import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class ArticleService {

    @Autowired
    private MiscProperties miscProperties;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ArticleRepository articleRepository;
    @Autowired
    private UserRepository userRepository;

    private SnowFlake snowFlake = new SnowFlake(1, 1);
    /**
     * 查询文章列表
     * @param pageable
     * @param criteria
     * @return
     */
    public Page<ArticleDO> queryArticle (Pageable pageable, ArticleQueryCriteriaDTO criteria) {
        ArticleSpecification specification = new ArticleSpecification(criteria);
        return articleRepository.findAll(specification, pageable);
    }

    public ArticleDO findArticleById(long id) {
        return articleRepository.findById(id)
                .orElseThrow(() -> new LocalServiceException("article.not-found", new Object[]{id}));
    }

    public ArticleDO createArticle(OnlineUserVO user, CreateArticleDTO dto) {

        CategoryDO category = categoryRepository.findByIdOrElseDefault(dto.getCategory());

        ArticleDO article = new ArticleDO();
        article.setTitle(dto.getTitle());

        article.setTags(String.join("#", dto.getTags()));

        article.setShortChain(String.valueOf(snowFlake.nextId()));
        article.setType(dto.getType());
        // 默认是草稿
        article.setStatus(ArticleDO.Status.DRAFT);
        // 默认打开
        article.setCommentStatus(dto.getCommentStatus());

        article.setEyeCount(0);
        article.setLikeCount(0);
        article.setCommentCount(0);
//        article.setFixedTop(false);
        article.setCreatedAt(new Date());
        article.setUpdatedAt(new Date());
        article.setDeletedAt(null);
        article.setSeqInManage(0);
        article.setVer(miscProperties.getUserVer());


        UserDO userDO = userRepository.findById(user.getId()).orElseThrow(() -> new LocalServiceException("user.not-found", new Object[]{user.getId()}));

        article.setUser(userDO);
        article.setCategory(category);


        ArticleDO.Attribute attribute = new ArticleDO.Attribute();
        attribute.setCovers(dto.getCovers());
        attribute.setCoverPosition(dto.getCoverPosition());
        attribute.setFixedTop(false);

        article.setAttribute(attribute);


        articleRepository.save(article);
        return article;
    }

    public void deleteArticleById(long id) {
        // 删除文章
        if (articleRepository.existsById(id)) {
            articleRepository.deleteById(id);
        }
    }

    public ArticleDO updateArticleById (long id, UpdateArticleDTO dto) {
        ArticleDO article = this.findArticleById(id);

        CategoryDO category = categoryRepository.findByIdOrElseDefault(dto.getCategory());
        article.setCategory(category);

        if (StringUtils.isNotEmpty(dto.getAlias())) {
            article.setShortChain(dto.getAlias());
        }

        article.setTitle(dto.getTitle());

        article.setType(dto.getType());
        article.setTags(String.join("#", dto.getTags()));
        article.setCommentStatus(dto.getCommentStatus());
        article.setSeqInManage(dto.getSeqInManage());

        ArticleDO.Attribute attribute = article.getAttribute();

        attribute.setFixedTop(dto.isFixedTop());
        attribute.setCoverPosition(dto.getCoverPosition());
        attribute.setCovers(dto.getCovers());


        article.setStatus(dto.getStatus());

        articleRepository.save(article);
        return article;
    }

    public ArticleDO updateArticleContentById(long id, UpdateArticleContentDTO dto) {
        ArticleDO article = this.findArticleById(id);
        article.setContent(dto.getContent());
        articleRepository.save(article);
        return article;
    }

    public ArticleDO updateArticleStatusById(long id, UpdateArticleStatusDTO dto) {
        ArticleDO article = this.findArticleById(id);
        article.setStatus(dto.getStatus());
        articleRepository.save(article);
        return null;
    }

    @AllArgsConstructor
    class ArticleSpecification implements Specification<ArticleDO> {
        private final ArticleQueryCriteriaDTO criteria;
        @Override
        public Predicate toPredicate(Root<ArticleDO> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
            Predicate predicate = criteriaBuilder.conjunction();
            List<Expression<Boolean>> expressions = predicate.getExpressions();

            if(StringUtils.isNotEmpty(criteria.getKeyword())) {
                Predicate[] predicates = new Predicate[3];
                String value = "%" + criteria.getKeyword() + "%";
                // 标题 and 标签
                predicates[0] = criteriaBuilder.like(root.get("title"), value);
                predicates[1] = criteriaBuilder.like(root.get("tags"), value);
                predicates[2] = criteriaBuilder.like(root.get("shortChain"), value);
                expressions.add(criteriaBuilder.or(predicates));
            }

            if (criteria.getKeyword() != null) {
                final Join<CategoryDO, ArticleDO> jo = root.join("category", JoinType.LEFT);
                expressions.add(criteriaBuilder.equal(jo.get("id"), criteria.getCategory()));
            }

            if (criteria.getStatus() != null) {
                expressions.add(criteriaBuilder.equal(root.get("status"), criteria.getStatus()));
            }

            if (criteria.getType() != null) {
                expressions.add(criteriaBuilder.equal(root.get("type"), criteria.getType()));
            }
            expressions.add(criteriaBuilder.equal(root.get("deletedAt"), criteriaBuilder.nullLiteral(Date.class)));

            query.where(predicate);
            return query.getRestriction();
        }
    }
}

