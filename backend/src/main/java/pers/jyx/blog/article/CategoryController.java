package pers.jyx.blog.article;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pers.jyx.blog.Constant;
import pers.jyx.blog.article.model.dto.CategoryQueryCriteriaDTO;
import pers.jyx.blog.article.model.CategoryDO;
import pers.jyx.blog.article.model.dto.CreateCategoryDTO;
import pers.jyx.blog.article.model.dto.UpdateCategoryDTO;
import pers.jyx.blog.user.model.UserRole;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Slf4j
@RestController
@RequestMapping(value = Constant.API_SERVLET_URL_PREFIX+ "/category")
@PreAuthorize("hasRole('"+ UserRole.USER +"')")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    /**
     * 获取文章列表
     * @return
     */
    @GetMapping
    public Page<CategoryDO> getCategory (Pageable pageable, CategoryQueryCriteriaDTO criteria) {
        return categoryService.queryCategory(pageable, criteria);
    }

    /**
     * 查询文章
     * @return
     */
    @GetMapping(value = "/{id}")
    public CategoryDO one (@PathVariable("id") @Validated @NotNull String id) {
        return categoryService.findCategoryById(id);
    }

    /**
     * 新建
     * @return
     */
    @PostMapping
    public CategoryDO create (@Validated @RequestBody CreateCategoryDTO createCategory) {
        return categoryService.createCategory(createCategory);
    }

    /**
     * 删除文章
     * @return
     */
    @DeleteMapping(value = "/{id}")
    public void delete (@PathVariable("id") @Validated @NotEmpty String id) {
        categoryService.deleteCategoryById(id);
    }


    @PutMapping(value = "/{id}")
    public CategoryDO update(@PathVariable("id") @Validated @NotEmpty String id, @Validated @RequestBody UpdateCategoryDTO updateCategoryDTO) {
        return categoryService.updateCategoryById(id, updateCategoryDTO);
    }
}
