package pers.jyx.blog.article.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pers.jyx.blog.article.model.ArticleDO;

@Repository
public interface ArticleRepository extends JpaRepository<ArticleDO, Long>, JpaSpecificationExecutor<ArticleDO> {

    /**
     * 修改文章分类
     *
     * @param id    原分类
     * @param newId 新分类
     */
    @Query(value = "update #{#entityName} a set a.category.id = ?2 where a.category.id = ?1")
    @Modifying
    @Transactional(rollbackFor = Exception.class)
    void moveCategory(String id, String newId);
}
