package pers.jyx.blog.comment;

import cn.mbdoge.jyx.jwt.SecurityUtils;
import cn.mbdoge.jyx.jwt.User;
import cn.mbdoge.jyx.web.util.HttpUtils;
import cn.mbdoge.jyx.web.util.IpUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pers.jyx.blog.Constant;
import pers.jyx.blog.article.ArticleService;
import pers.jyx.blog.article.model.dto.ArticleDO;
import pers.jyx.blog.article.model.dto.ArticleQueryCriteriaDTO;
import pers.jyx.blog.article.model.dto.CreateArticleDTO;
import pers.jyx.blog.comment.model.CommentDO;
import pers.jyx.blog.comment.model.CommentQueryCriteriaDTO;
import pers.jyx.blog.comment.model.CreateCommentDTO;
import pers.jyx.blog.user.model.OnlineUserVO;
import pers.jyx.blog.user.model.UserRole;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestController
@RequestMapping(value = Constant.API_SERVLET_URL_PREFIX+ "/comment")
public class CommentController {

    @Autowired
    private CommentService commentService;

    /**
     * 获取全部评论列表
     * @return
     */
    @GetMapping
    @PreAuthorize("hasRole('"+ UserRole.ADMIN +"')")
    public Page<CommentDO> getCommentAll (@PathVariable("id") String id, Pageable pageable, @Validated CommentQueryCriteriaDTO criteria) {
        return commentService.queryComment(pageable, criteria);
    }

    /**
     * 获取文章评论列表
     * @return
     */
    @GetMapping(value = "/{id}")
    public Page<CommentDO> getCommentByTarget (@PathVariable("id") String id, Pageable pageable, @Validated CommentQueryCriteriaDTO criteria) {
        criteria.setTarget(id);
        return commentService.queryComment(pageable, criteria);
    }

    /**
     * 新建
     * @return
     */
    @PostMapping
    @PreAuthorize("hasRole('"+ UserRole.GUEST +"')")
    public CommentDO create (@RequestBody @Validated CreateCommentDTO createComment, Authentication authentication) {
//        用户信息还有待处理 ，完全信任用户的数据是不可能的
//        来自用户的cookie 信息 特别处理
        OnlineUserVO user = (OnlineUserVO) authentication.getPrincipal();

        return commentService.createComment(user, createComment);
    }
}
