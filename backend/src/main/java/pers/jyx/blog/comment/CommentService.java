package pers.jyx.blog.comment;

import cn.mbdoge.jyx.exception.LocalServiceException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import pers.jyx.blog.article.ArticleService;
import pers.jyx.blog.article.model.dto.ArticleDO;
import pers.jyx.blog.article.model.dto.ArticleQueryCriteriaDTO;
import pers.jyx.blog.comment.model.CommentDO;
import pers.jyx.blog.comment.model.CommentQueryCriteriaDTO;
import pers.jyx.blog.comment.model.CreateCommentDTO;
import pers.jyx.blog.user.model.OnlineUserVO;
import pers.jyx.blog.user.model.UserDO;
import pers.jyx.blog.user.model.UserRepository;

import javax.persistence.criteria.*;
import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private UserRepository userRepository;

    public Page<CommentDO> queryComment (Pageable pageable, CommentQueryCriteriaDTO criteria) {
        CommentSpecification specification = new CommentSpecification(criteria);
        return commentRepository.findAll(specification, pageable);
    }

    public CommentDO createComment(OnlineUserVO user, CreateCommentDTO createComment) {

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
        return null;
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


            query.where(predicate);
            return query.getRestriction();
        }
    }
}
