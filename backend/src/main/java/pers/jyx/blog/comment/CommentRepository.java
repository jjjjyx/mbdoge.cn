package pers.jyx.blog.comment;

import cn.mbdoge.jyx.exception.LocalServiceException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import pers.jyx.blog.article.model.CategoryDO;
import pers.jyx.blog.comment.model.CommentDO;

import static pers.jyx.blog.Constant.DEFAULT_CATEGORY_ID;

@Repository
public interface CommentRepository extends JpaRepository<CommentDO, String>, JpaSpecificationExecutor<CommentDO> {

}
