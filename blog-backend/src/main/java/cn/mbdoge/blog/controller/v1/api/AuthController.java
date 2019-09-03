package cn.mbdoge.blog.controller.v1.api;


import cn.mbdoge.blog.AppConfig;
import cn.mbdoge.blog.model.dao.ConfigRepository;
import cn.mbdoge.blog.model.entities.UserEntity;
import cn.mbdoge.blog.model.pojo.ConfigKey;
import cn.mbdoge.blog.model.pojo.RespResult;
import cn.mbdoge.blog.model.pojo.UserDto;
import cn.mbdoge.blog.service.AuthService;
import cn.mbdoge.blog.service.UserDetailsServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.util.WebUtils;

import javax.servlet.ServletContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.security.Principal;

/**
 * @author jyx
 */
@RequestMapping(value = AppConfig.API_SERVLET_URL_PREFIX+ "/auth")
@RestController
@EnableAutoConfiguration
@Slf4j

public class AuthController {

    @Value("${user.auth.header}")
    private String userAuthHeaderKey;
    @Value("${server.servlet.session.cookie.domain}")
    private String cookieDomain;


    private final ConfigRepository configRepository;

    private final AuthService authService;
    private final UserDetailsServiceImpl userDetailsService;
    private final ObjectMapper objectMapper;

    public AuthController(ConfigRepository configRepository, AuthService authService, UserDetailsServiceImpl userDetailsService, ObjectMapper objectMapper) {
        this.configRepository = configRepository;
        this.authService = authService;
        this.userDetailsService = userDetailsService;
        this.objectMapper = objectMapper;
    }

    @PostMapping
    public String createAuthenticationToken(@RequestParam("username") String username, @RequestParam("password") String password, HttpServletResponse response) throws AuthenticationException, JsonProcessingException {
        String token = authService.login(username, password);
        Cookie cookie = new Cookie(userAuthHeaderKey, token);
        cookie.setHttpOnly(true);
//        cookie.setSecure(true);
        cookie.setMaxAge(5 * 60 * 60); // expires in 5 hour
        cookie.setPath("/");
        cookie.setDomain(cookieDomain);
        response.addCookie(cookie);
        return token;
    }

    @GetMapping
    @PreAuthorize("hasRole('USER')")
    public UserDetails verificationToken(Principal principal) throws AuthenticationException {
        log.debug("user info = {}", principal);
        return userDetailsService.loadUserByUsername(principal.getName());
    }

    @PostMapping(value = "logout")
    @PreAuthorize("hasRole('USER')")
    public String logout(Principal principal, HttpSession session, HttpServletResponse response) throws AuthenticationException {
        log.debug("user info = {} logout", principal);
        session.invalidate();

        Cookie cookie = new Cookie(userAuthHeaderKey, null);
        cookie.setHttpOnly(true);
//        cookie.setSecure(true);
        cookie.setMaxAge(0); // expires in 5 hour
        cookie.setPath("/");
        cookie.setDomain(cookieDomain);
        response.addCookie(cookie);
//        Cookie cookie = WebUtils.getCookie(request, userAuthHeaderKey);

        SecurityContextHolder.clearContext();
        return "0";
    }

    @RequestMapping(value = "/Gq79OrdECi8lwe4EY0Jh", method = RequestMethod.POST)
    @PreAuthorize("hasRole('USER')")
    public UserEntity register(@Validated UserDto addedUser) throws AuthenticationException, NoHandlerFoundException {
//        根据配置决定是否启用

        if (configRepository.isEnableRegister()) {
            UserEntity userEntity = authService.register(addedUser);
            configRepository.save(ConfigKey.ENABLE_REGISTER, Boolean.FALSE.toString());
            return userEntity;
        }
        throw new NoHandlerFoundException(RequestMethod.POST.toString(), "", null);
    }
}
