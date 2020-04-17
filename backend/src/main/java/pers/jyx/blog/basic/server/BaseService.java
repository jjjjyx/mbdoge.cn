package pers.jyx.blog.basic.server;


import cn.mbdoge.jyx.jwt.SecurityUtils;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import pers.jyx.blog.user.model.OnlineUserVO;
import pers.jyx.blog.user.model.UserDO;
import pers.jyx.blog.user.model.UserRepository;
import pers.jyx.blog.user.model.UserRole;

import javax.persistence.criteria.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * 抽象的不是很好，耦合了用户模块，可以修改下
 *
 * @author jyx
 */
public abstract class BaseService {

    @Autowired
    protected UserRepository userRepository;

    public OnlineUserVO getCurrentOnlineUser() {
        return (OnlineUserVO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }


    /**
     * 获取当前用户名称
     *
     * @return username
     */
    public String getCurrentUsername() {
        return SecurityUtils.getUsername();
    }

    /**
     * 获取当前用户实例
     *
     * @return user
     */
    public UserDO getCurrentUser() {
        String username = this.getCurrentUsername();
        return userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(String.format("No user found with username '%s'.", username)));
    }

    /**
     * 是否包含管理员权限
     *
     * @return boolean true 是管理员
     */
    public boolean isAdmin() {
        return SecurityUtils.getUsernameAuthorities().stream().anyMatch(o -> o.getAuthority().equals(UserRole.ADMIN));
    }

    /**
     * 管理员查询支持对非本用户的查询
     */

    public <T> Page<T> query(Pageable pageable, BaseSpecification<T> specification) {
        assert specification != null;
        JpaSpecificationExecutor<T> jpaSpecificationExecutor = specification.getJpaSpecificationExecutor();
        assert jpaSpecificationExecutor != null;
        return jpaSpecificationExecutor.findAll(specification, pageable);
    }

    /**
     * 获取当前请求req
     *
     * @return req
     */
    public HttpServletRequest getHttpReq() {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        assert requestAttributes != null;
        return requestAttributes.getRequest();

    }

    /**
     * 获取当前请求session
     *
     * @return req
     */
    public HttpSession getHttpSession() {
        return this.getHttpReq().getSession();
    }

    /**
     * 对管理员权限的封装，注意查询实现Bean 有所属用户，且字段名称叫 user
     *
     * @param <T>
     */
    @AllArgsConstructor
    public abstract class BaseSpecification<T> implements Specification<T> {

        /**
         * 返回说明
         *
         * @return
         */
        public abstract String getTargetName();

        /**
         * 返回用户对象
         *
         * @return
         */
        public abstract String getUsername();

        /**
         * 返回 JpaSpecificationExecutor
         *
         * @return 实现了 JpaSpecificationExecutor 接口的Repository
         */
        public abstract JpaSpecificationExecutor<T> getJpaSpecificationExecutor();

        /**
         * 返回查询的条件
         *
         * @param root            root
         * @param criteriaBuilder cb
         * @return list
         */
        public abstract List<Expression<Boolean>> toExpressions(Root<T> root, CriteriaBuilder criteriaBuilder);

        /**
         * 查询默认实现
         *
         * @param root            root
         * @param query           q
         * @param criteriaBuilder cb
         * @return p
         */
        @Override
        public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
            Predicate predicate = criteriaBuilder.conjunction();
            List<Expression<Boolean>> expressions = predicate.getExpressions();

            List<Expression<Boolean>> list = this.toExpressions(root, criteriaBuilder);
            if (list != null) {
                expressions.addAll(list);
            }

            // todo 这里耦合严重
            // 非管理员只能查询自己的，
            if (!BaseService.this.isAdmin()) {
                // 查询时 需要带上当前用户用户
                addUserQuery(root, criteriaBuilder, expressions, BaseService.this.getCurrentUsername());
            } else {
                // 管理员级别 如果提交了username 允许查询
                String username = this.getUsername();
                if (!StringUtils.isEmpty(username)) {
                    addUserQuery(root, criteriaBuilder, expressions, username);
                }
            }

            query.where(predicate);
            return query.getRestriction();
        }

        private void addUserQuery(Root<T> root, CriteriaBuilder criteriaBuilder, List<Expression<Boolean>> expressions, String currentUsername) {
            final Join<UserDO, T> jo = root.join("user", JoinType.LEFT);
            assert jo != null;
            expressions.add(criteriaBuilder.equal(jo.get("username"), currentUsername));
        }
    }

}
