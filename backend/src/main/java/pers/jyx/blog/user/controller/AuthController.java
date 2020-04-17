package pers.jyx.blog.user.controller;

import cn.mbdoge.jyx.exception.LocalServiceException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pers.jyx.blog.Constant;
import pers.jyx.blog.MiscProperties;
import pers.jyx.blog.user.model.*;
import pers.jyx.blog.user.server.AccountServiceImpl;

import javax.servlet.http.HttpServletResponse;
import java.util.Collections;


/**
 * 用户授权相关 api
 *
 * @author jyx
 */
@Api(value = "用户授权中心")
@Slf4j
@RequestMapping(value = Constant.API_SERVLET_URL_PREFIX + "/auth")
@RestController
public class AuthController {
    @Autowired
    private AccountServiceImpl accountServiceImpl;
    @Autowired
    private MessageSourceAccessor messageSourceAccessor;
    @Autowired
    private MiscProperties miscProperties;
    @Value("${mbdoge.web.security.jwt.expiration}")
    private long expiration;

    //    @Log(title = "用户登录", banField = {"password"}, businessType = SysOperationLogDO.BusinessType.LOGIN)
    @ApiOperation(value = "用户登录接口", notes = "输入账号密码，进行登录")
    @PostMapping(produces = "application/json")
    public String createAuthenticationToken(@RequestBody AuthDTO auth, HttpServletResponse response) {

//        Cookie cookie = new Cookie(miscProperties.getAuthCookieKey(), token);
//        cookie.setHttpOnly(true);
//        cookie.setMaxAge((int) expiration); // expires in 5 hour
//        cookie.setPath("/");
//        cookie.setDomain("." + Constant.DOMAIN);
//        response.addCookie(cookie);
//        String token = ;

        return accountServiceImpl.login(auth.getUsername(), auth.getPassword()); // new TokenVO(token);
    }

    /**
     * 注册游客
     *
     * @param registerUser
     * @return
     */
    @ApiOperation(value = "游客注册", notes = "游客输入信息后，保存游客信息，并生成token 此token 为一次性token，即丢失后，神仙也找不回来了")
    @PostMapping(value = "guest", params = "role=guest", produces = "application/json")
    public String reg(@Validated @RequestBody RegisterUserDTO registerUser) {
        // 如果没有提交密码 那么是游客，随机生成一个密码， 并返回一个 token 给定30 天的时间
        // 固定角色为 guest
        registerUser.setRoles(Collections.singletonList(UserRole.GUEST.toLowerCase()));
        registerUser.setExpire(Constant.GUEST_TOKEN_EXPIRE);
        // 随机密码
        String password = RandomStringUtils.randomAlphanumeric(22);
        String username = Constant.GUEST_INFO_PREFIX + RandomStringUtils.randomAlphanumeric(2) + " " + registerUser.getUsername();

        registerUser.setUsername(username);
        registerUser.setPassword(password);

        try {
            accountServiceImpl.register(registerUser);
        } catch (LocalServiceException e) {
            log.warn("注册游客失败！", e);
            throw new LocalServiceException("guest.reg.fail");
        }

        return accountServiceImpl.login(username, password);
    }

    //    @Log(title = "获取登录状态")
    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public OnlineUserVO verificationToken(Authentication authentication) {
        log.debug("user info = {}", authentication);
        return (OnlineUserVO) authentication.getPrincipal();
    }

}
