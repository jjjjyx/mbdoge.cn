package pers.jyx.blog.article.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import pers.jyx.blog.article.model.CategoryDO;

@Repository
public interface CategoryRepository extends JpaRepository<CategoryDO, String>, JpaSpecificationExecutor<CategoryDO> {

}
