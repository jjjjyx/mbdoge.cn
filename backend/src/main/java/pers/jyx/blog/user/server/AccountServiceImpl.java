package pers.jyx.blog.user.server;

import cn.mbdoge.jyx.exception.LocalServiceException;
import cn.mbdoge.jyx.jwt.JwtTokenProvider;
import cn.mbdoge.jyx.web.util.IpUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import pers.jyx.blog.Constant;
import pers.jyx.blog.MiscProperties;
import pers.jyx.blog.basic.model.ConfigKey;
import pers.jyx.blog.basic.model.ConfigRepository;
import pers.jyx.blog.basic.server.BaseService;
import pers.jyx.blog.user.model.*;

import javax.persistence.criteria.*;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

/**
 * @author jyx
 */
@Service
@Slf4j
public class AccountServiceImpl extends BaseService {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private ConfigRepository configRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private MiscProperties miscProperties;

    /**
     * 用户登录，返回 token
     * 自动保存到 redis
     * @param username 账号
     * @param password 密码
     * @return token
     */
    public String login(String username, String password) {
        // BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        log.debug("login username = {} password = **", username);

        UsernamePasswordAuthenticationToken upToken = new UsernamePasswordAuthenticationToken(username, password);

        final Authentication authentication = authenticationManager.authenticate(upToken);
        OnlineUserVO userDetails = (OnlineUserVO) authentication.getPrincipal();

        log.debug("login userDetails = {}", userDetails);
        userDetails.setUid(UUID.randomUUID().toString());
        // 设置用户的相关信息
        userDetails.setLoginTime(new Date());
        HttpServletRequest req = this.getHttpReq();
        userDetails.setIpAddr(IpUtils.getRequestRealAddress(req));
        userDetails.setBrowser(req.getHeader("User-Agent"));

        List<String> roles = userDetails.getRoles();
        String role = roles.get(0);
        // 游客 的签名时间更长，因为是一次性的
        // 游客登录
        if (UserRole.GUEST.equals(role)) {
            return jwtTokenProvider.createToken(userDetails, userDetails.getUid(), Constant.GUEST_TOKEN_EXPIRE);
        }

        return jwtTokenProvider.createToken(userDetails, userDetails.getUid());
    }

    /**
     * 清除当前登录的账号信息
     */
    public void clearCurrentUser() {

        // 退出登录是没法控制，比较token 生成后是没有记录状态的，退出也仅仅是清除了本次的认证，
        // 只要用户仍然拿者token 去请求，就还是算登录
        OnlineUserVO onlineUserVO = this.getCurrentOnlineUser();
        log.info("用户 = {} 退出登录", onlineUserVO.getUsername());

        jwtTokenProvider.clearUser(onlineUserVO, onlineUserVO.getUid());

        this.getHttpSession().invalidate();
        SecurityContextHolder.clearContext();
    }

    /**
     * 更新用户信息 更新密码会使当前登录的账号失效
     * @param updateUserDTO
     * @return
     */
    public UserDO updateUser(UpdateUserDTO updateUserDTO) {
        UserDO userDO = this.getCurrentUser();

        if (!StringUtils.isEmpty(updateUserDTO.getDisplayName())) {
            userDO.setDisplayName(updateUserDTO.getDisplayName());
        }

        if (!StringUtils.isEmpty(updateUserDTO.getNickname())) {
            userDO.setNickname(updateUserDTO.getNickname());
        }

        if (!StringUtils.isEmpty(updateUserDTO.getPassword())) {
            userDO.setPassword(passwordEncoder.encode(updateUserDTO.getPassword()));
            this.clearCurrentUser();
        }

        userRepository.save(userDO);
        return userDO;
    }

    /**
     * 注册用户
     * @param registerUser
     * @return
     */
    public UserDO register(RegisterUserDTO registerUser) {
        log.debug("注册用户 User = {}", registerUser);
        final String username = registerUser.getUsername();

        Optional<UserDO> user = userRepository.findByUsername(username);

        if (user.isPresent()) {
            log.debug("用户已存在，注册失败");
            throw new LocalServiceException("auth.register.fail");
        }

        final String rawPassword = registerUser.getPassword();
        UserDO newUser = new UserDO();
        newUser.setUsername(username);
        newUser.setPassword(passwordEncoder.encode(rawPassword));

        if (StringUtils.isEmpty(registerUser.getNickname())) {
            newUser.setNickname(username);
        } else {
            newUser.setNickname(registerUser.getNickname());
        }

        if (StringUtils.isEmpty(registerUser.getDisplayName())) {
            newUser.setDisplayName(username);
        } else {
            newUser.setNickname(registerUser.getDisplayName());
        }
        // 默认30天
        long expire = Optional.ofNullable(registerUser.getExpire())
                .orElseGet(() -> Long.parseLong(configRepository.getValueByKey(ConfigKey.SETTING_USER_DEFAULT_EXPIRE)));

        if (registerUser.getRoles().stream().anyMatch(UserRole.ADMIN::equalsIgnoreCase)) {
            log.debug("注册失败，不可注册管理员权限");
            throw new LocalServiceException("auth.register.fail");
        }


        List<RoleDO> roles = roleRepository.findAllByNameIn(registerUser.getRoles());
        // 不允许包含管理员？
        if (roles.isEmpty()) {
            throw new LocalServiceException("auth.register.fail.role.not-blank");
        }
        newUser.setRoles(roles);
        newUser.setNextExpireTime(new Date(System.currentTimeMillis() + expire));
        newUser.setCreatedAt(new Date());
        newUser.setUpdatedAt(new Date());
        newUser.setUserStatus(0);
        newUser.setVer(miscProperties.getUserVer());

        UserDetailDO userDetail = configRepository.getDefaultUserDetail();

        userDetail.setUser(newUser);

        newUser.setUserDetail(userDetail);

        userRepository.save(newUser);
        log.debug("注册成功 下次到期时间 {}", newUser.getNextExpireTime());
        return newUser;
    }


    /**
     * 用户查询
     */
    @AllArgsConstructor
    class UserSpecification extends BaseSpecification<UserDO> {
        private final UserQueryCriteriaDTO criteria;

        @Override
        public String getTargetName() {
            return "查询用户列表";
        }

        @Override
        public String getUsername() {
            return null;
        }

        @Override
        public JpaSpecificationExecutor<UserDO> getJpaSpecificationExecutor() {
            return userRepository;
        }

        @Override
        public List<Expression<Boolean>> toExpressions(Root<UserDO> root, CriteriaBuilder criteriaBuilder) {

            List<Expression<Boolean>> expressions = new ArrayList<>();
            if (!StringUtils.isEmpty(criteria.getName())) {
                Predicate[] predicates = new Predicate[3];
                String value = "%" + criteria.getName() + "%";
                predicates[0] = criteriaBuilder.like(root.get("username"), value);
                predicates[1] = criteriaBuilder.like(root.get("nickname"), value);
                predicates[2] = criteriaBuilder.like(root.get("displayName"), value);
                expressions.add(criteriaBuilder.or(predicates));
            }
            if (!StringUtils.isEmpty(criteria.getRoleName())) {
                // 按角色查询
                final Join<UserDO, RoleDO> jo = root.join("roles", JoinType.INNER);
                expressions.add(criteriaBuilder.equal(jo.get("name").as(String.class), criteria.getRoleName()));
            }

            return expressions;
        }
    }

}
