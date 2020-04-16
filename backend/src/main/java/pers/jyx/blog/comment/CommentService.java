package pers.jyx.blog.comment;

import cn.mbdoge.jyx.exception.LocalServiceException;
import cn.mbdoge.jyx.exception.RequestLimitException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import pers.jyx.blog.article.ArticleService;
import pers.jyx.blog.article.model.dto.ArticleDO;
import pers.jyx.blog.article.model.dto.ArticleQueryCriteriaDTO;
import pers.jyx.blog.comment.model.CommentDO;
import pers.jyx.blog.comment.model.CommentQueryCriteriaDTO;
import pers.jyx.blog.comment.model.CreateCommentDTO;
import pers.jyx.blog.comment.model.UpdateCommentDTO;
import pers.jyx.blog.user.model.OnlineUserVO;
import pers.jyx.blog.user.model.UserDO;
import pers.jyx.blog.user.model.UserRepository;

import javax.persistence.criteria.*;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    public Page<CommentDO> queryComment (Pageable pageable, CommentQueryCriteriaDTO criteria) {
        CommentSpecification specification = new CommentSpecification(criteria);
        return commentRepository.findAll(specification, pageable);
    }

    public CommentDO createComment(OnlineUserVO user, CreateCommentDTO createComment) {
        // 重复内容过滤
        // 一个内容在 3 分钟内重复发送 1 次以上是不允许的
        // 用户id + 评论目标 + 评论内容 hash

        // todo 这里只限制了 重复内容，频繁的发布也需要限制，这个考虑做在 mbdoge starter 上
        String key = user.getUid() + createComment.getTarget() + Objects.hash(createComment.getContent());
        boolean hasKey = Optional.ofNullable(redisTemplate.hasKey(key)).orElse(false);
        if (hasKey) {
            throw new RequestLimitException("comment.duplicate.content");
        } else {
            ValueOperations<String, Object> valueOperations = redisTemplate.opsForValue();
            valueOperations.set(key, true, 3, TimeUnit.MINUTES);
        }

        CommentDO comment = new CommentDO();

        comment.setTarget(createComment.getTarget());
        comment.setContent(createComment.getContent());
        // 通过审核后才允许有楼层
        comment.setKarma(-1);
        comment.setCreatedAt(new Date());
        commentRepository.findById(createComment.getParent()).ifPresent(comment::setParent);
//        comment.setAuthor(createComment.getAuthor());
//        comment.setAvatar(createComment.getAvatar());
//        comment.setEmail(createComment.getEmail());
//        comment.setUrl(createComment.getUrl());
        UserDO userDO = userRepository.findById(user.getId()).orElseThrow(() -> new LocalServiceException("user.not-found", new Object[]{user.getId()}));

        comment.setUser(userDO);
        comment.setIp(user.getIpAddr());
        comment.setUa(user.getAvatar());

        comment.setStatus(CommentDO.Status.AUDIT);

        commentRepository.save(comment);

        return comment;
    }

    public CommentDO findCommentById(Long id) {
        return commentRepository.findById(id)
                .orElseThrow(() -> new LocalServiceException("article.not-found", new Object[]{id}));
    }
    /**
     * // 自动过审批有点担心
     *
     * 更新评论 主要更新评论的状态
     * @param id
     * @param dto
     * @return
     */
    public CommentDO updateComment(Long id, UpdateCommentDTO dto) {

        CommentDO commentDO = commentRepository.findById(id).orElseThrow(() -> new LocalServiceException("comment.not-found"));
        commentDO.setStatus(dto.getStatus());
        commentRepository.save(commentDO);
        return commentDO;
    }

    public CommentDO approveComment(Long id, CommentDO.Status status) {
        CommentDO commentDO = this.findCommentById(id);

//        switch (status) {
//            case DISPLAY:
//                // 如果 是批准
//                break;



//        }
        if (commentDO.getStatus() == CommentDO.Status.AUDIT && status == CommentDO.Status.DISPLAY) {
            // 完善楼层信息
            String target = commentDO.getTarget();
            CommentDO parent = commentDO.getParent();
            // 查询 当前评论对象的个数
            final int i = commentRepository.countByParentAndTarget(parent, target);
            commentDO.setKarma(i + 1);
        }
        commentDO.setStatus(status);

        commentRepository.save(commentDO);
        return commentDO;
    }


    @AllArgsConstructor
    class CommentSpecification implements Specification<CommentDO> {
        private final CommentQueryCriteriaDTO criteria;

        @Override
        public Predicate toPredicate(Root<CommentDO> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
            Predicate predicate = criteriaBuilder.conjunction();
            List<Expression<Boolean>> expressions = predicate.getExpressions();

            if(StringUtils.isNotEmpty(criteria.getKeyword())) {
                Predicate[] predicates = new Predicate[1];
                String value = "%" + criteria.getKeyword() + "%";
                // 标题 and 标签
                predicates[0] = criteriaBuilder.like(root.get("content"), value);

                expressions.add(criteriaBuilder.or(predicates));
            }

            if(StringUtils.isNotEmpty(criteria.getAuthor())) {
                Predicate[] predicates = new Predicate[2];
                String value = "%" + criteria.getAuthor() + "%";
                // 标题 and 标签
                predicates[0] = criteriaBuilder.like(root.get("author"), value);
                predicates[1] = criteriaBuilder.like(root.get("email"), value);

                expressions.add(criteriaBuilder.or(predicates));
            }

            if(StringUtils.isNotEmpty(criteria.getTarget())) {
                expressions.add(criteriaBuilder.equal(root.get("target"), criteria.getTarget()));
            }

            if (criteria.getStatus() != null) {
                expressions.add(criteriaBuilder.equal(root.get("status"), criteria.getTarget()));
            }

            query.where(predicate);
            return query.getRestriction();
        }
    }
}
