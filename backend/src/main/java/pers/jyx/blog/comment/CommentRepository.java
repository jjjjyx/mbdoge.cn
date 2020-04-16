package pers.jyx.blog.comment;

import cn.mbdoge.jyx.exception.LocalServiceException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import pers.jyx.blog.article.model.CategoryDO;
import pers.jyx.blog.comment.model.CommentDO;

import static pers.jyx.blog.Constant.DEFAULT_CATEGORY_ID;

@Repository
public interface CommentRepository extends JpaRepository<CommentDO, Long>, JpaSpecificationExecutor<CommentDO> {

    /**
     * 查询楼层信息
     * @param parent 父评论
     * @param target 评论对象
     * @param status 评论状态
     * @return
     */
    int countByParentAndTargetAndStatus(CommentDO parent, String target, CommentDO.Status status);

    /**
     * 查询楼层信息 只统计审核通过的评论
     * @param parent 父评论
     * @param target 评论对象
     * @return
     */
    default int countByParentAndTarget (CommentDO parent, String target) {
        return this.countByParentAndTargetAndStatus(parent, target, CommentDO.Status.DISPLAY);
    }
}
