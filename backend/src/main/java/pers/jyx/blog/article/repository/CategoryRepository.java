package pers.jyx.blog.article.repository;

import cn.mbdoge.jyx.exception.LocalServiceException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import pers.jyx.blog.article.model.CategoryDO;

import static pers.jyx.blog.Constant.DEFAULT_CATEGORY_ID;

@Repository
public interface CategoryRepository extends JpaRepository<CategoryDO, String>, JpaSpecificationExecutor<CategoryDO> {

    default CategoryDO findByIdOrElseDefault(String id) {
        return this.findById(id)
                .orElseGet(() -> this.findById(DEFAULT_CATEGORY_ID)
                        .orElseThrow(() -> new LocalServiceException("default.category.not-found")));
    }
}
