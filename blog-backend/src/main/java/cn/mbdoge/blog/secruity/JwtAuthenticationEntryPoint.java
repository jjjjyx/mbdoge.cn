package cn.mbdoge.blog.secruity;


import cn.mbdoge.blog.model.pojo.RespResult;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;


/**
 * @author jyx
 */
@Component
@Slf4j
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint, Serializable {

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MessageSourceAccessor messageSourceAccessor;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {

        /*
            全局的验证失败处理

            包含由 AuthenticationManager 解析来的错误
            以及自定义解析 token 出现的错误

            // 用户的3种状态 =》AccountStatusException
            LockedException
            DisabledException
            AccountExpiredException

            最终给出2种提示，
            403 凭证错误
            401 凭证过期 // 所有用户相关的验证都是过期
         */
        response.setContentType("application/json; charset=utf-8");
        String ret = e.getMessage();

        log.debug("JwtAuthenticationEntryPoint : message = {}, Exception = {}", e.getMessage(), e.getClass());
        // 设备类的凭证错误需要单独处理
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        PrintWriter out = response.getWriter();
        out.write(objectMapper.writeValueAsString(RespResult.error(ret)));
        out.close();
    }
}
