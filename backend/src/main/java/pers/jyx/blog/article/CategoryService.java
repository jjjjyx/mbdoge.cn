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
import pers.jyx.blog.article.model.CategoryDO;
import pers.jyx.blog.article.model.dto.CategoryQueryCriteriaDTO;
import pers.jyx.blog.article.model.dto.CreateCategoryDTO;
import pers.jyx.blog.article.model.dto.UpdateCategoryDTO;
import pers.jyx.blog.article.repository.ArticleRepository;
import pers.jyx.blog.article.repository.CategoryRepository;

import javax.persistence.criteria.*;
import java.util.List;

@Slf4j
@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ArticleRepository articleRepository;


    public Page<CategoryDO> queryCategory(Pageable pageable, CategoryQueryCriteriaDTO criteria) {
        CategorySpecification specification = new CategorySpecification(criteria);

        return categoryRepository.findAll(specification, pageable);
    }

    public CategoryDO findCategoryById(String id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new LocalServiceException("category.not-found", new Object[]{id}));
    }

    public CategoryDO createCategory(CreateCategoryDTO dto) {
        CategoryDO category = new CategoryDO();
        category.setDescription(dto.getDesc());
        category.setIcon(dto.getIcon());
        category.setName(dto.getName());
        categoryRepository.save(category);
        return category;
    }

    public void deleteCategoryById(String id) {
        log.debug("delete category id = {} ", id);
        // 移动分类下的文章至 默认分类
        if (StringUtils.isEmpty(id)) {
            log.warn("Default category can not be deleted！！");
            throw new LocalServiceException("delete.default.category");
        }
        if (categoryRepository.existsById(id)) {
            log.debug("迁移分类{}下文章至默认分类", id);
            articleRepository.moveCategory(id, "");
            categoryRepository.deleteById(id);
        }
    }

    public CategoryDO updateCategoryById(String id, UpdateCategoryDTO dto) {
        CategoryDO category = this.findCategoryById(id);
        category.setDescription(dto.getDesc());
        category.setIcon(dto.getIcon());
        category.setName(dto.getName());
        categoryRepository.save(category);
        return category;
    }

    @AllArgsConstructor
    class CategorySpecification implements Specification<CategoryDO> {
        private final CategoryQueryCriteriaDTO criteria;

        @Override
        public Predicate toPredicate(Root<CategoryDO> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
            Predicate predicate = criteriaBuilder.conjunction();
            List<Expression<Boolean>> expressions = predicate.getExpressions();

            if (StringUtils.isNotEmpty(criteria.getKeyword())) {
                Predicate[] predicates = new Predicate[2];
                String value = "%" + criteria.getKeyword() + "%";
                predicates[0] = criteriaBuilder.like(root.get("name"), value);
                predicates[1] = criteriaBuilder.like(root.get("description"), value);
                expressions.add(criteriaBuilder.or(predicates));
            }
            query.where(predicate);
            return query.getRestriction();
        }
    }
}
