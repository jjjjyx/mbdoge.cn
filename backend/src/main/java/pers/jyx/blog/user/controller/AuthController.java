package pers.jyx.blog.user.controller;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import pers.jyx.blog.Constant;
import pers.jyx.blog.user.model.OnlineUserVO;
import pers.jyx.blog.user.server.AccountServiceImpl;



/**
 * 用户授权相关 api
 * @author jyx
 */
@Slf4j
@RequestMapping(value = Constant.API_SERVLET_URL_PREFIX+ "/auth")
@RestController
public class AuthController {
    @Autowired
    private AccountServiceImpl accountServiceImpl;
    @Autowired
    private MessageSourceAccessor messageSourceAccessor;

//    @Log(title = "用户登录", banField = {"password"}, businessType = SysOperationLogDO.BusinessType.LOGIN)
    @PostMapping(produces = "application/json")
    public Object createAuthenticationToken(@RequestParam("username") String username, @RequestParam("password") String password) {
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
