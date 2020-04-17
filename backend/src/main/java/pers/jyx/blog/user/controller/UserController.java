package pers.jyx.blog.user.controller;

import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pers.jyx.blog.Constant;
import pers.jyx.blog.user.server.AccountServiceImpl;

import javax.servlet.http.HttpSession;
import java.security.Principal;

@Api(value = "用户相关api")
@Slf4j
@RequestMapping(value = Constant.API_SERVLET_URL_PREFIX + "/user")
@RestController
@PreAuthorize("isAuthenticated()")
public class UserController {
    @Autowired
    private AccountServiceImpl accountServiceImpl;

    @PostMapping(value = "logout")
    @PreAuthorize("isAuthenticated()")
    public String logout(Principal principal, HttpSession session) throws AuthenticationException {
        log.debug("user info = {} logout", principal);
        accountServiceImpl.clearCurrentUser();
        return "0";
    }
}
