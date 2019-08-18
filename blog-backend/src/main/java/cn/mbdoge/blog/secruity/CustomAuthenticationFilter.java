package cn.mbdoge.blog.secruity;


import cn.mbdoge.blog.AppConfig;
import cn.mbdoge.blog.exception.InvalidJwtAuthenticationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.util.AntPathMatcher;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author jyx
 */
@Slf4j
public class CustomAuthenticationFilter extends BasicAuthenticationFilter {

    private static final String BEARER = "bearer ";
    private static final String BASIC = "basic ";


    @Value("${user.auth.header}")
    private String userAuthHeaderKey;

    private final JwtTokenProvider jwtTokenProvider;
    private final MessageSourceAccessor messageSourceAccessor;

    private AntPathMatcher pathMatcher = new AntPathMatcher();

    public CustomAuthenticationFilter(AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider, MessageSourceAccessor messageSourceAccessor) {
        super(authenticationManager);
        this.jwtTokenProvider = jwtTokenProvider;
        this.messageSourceAccessor = messageSourceAccessor;
    }

    public CustomAuthenticationFilter(AuthenticationManager authenticationManager, AuthenticationEntryPoint authenticationEntryPoint, JwtTokenProvider jwtTokenProvider, MessageSourceAccessor messageSourceAccessor) {
        super(authenticationManager, authenticationEntryPoint);
        this.jwtTokenProvider = jwtTokenProvider;
        this.messageSourceAccessor = messageSourceAccessor;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        boolean debug = log.isDebugEnabled();
        String header = request.getHeader("Authorization");
        if (header == null) {
            filterChain.doFilter(request, response);
            return;
        }
        if (header.toLowerCase().startsWith(BEARER)) {
            String token = header.substring(BEARER.length());

            try {
                Authentication auth = jwtTokenProvider.getAuthentication(token);
                // 信任客户端传递
                if (debug) {
                    this.logger.debug("Authentication success: " + auth);
                }
                // 这里没有验证服务端限制的过期， 因为user的设计 没有过期项，也没有禁用相关
                SecurityContextHolder.getContext().setAuthentication(auth);
            } catch (InvalidJwtAuthenticationException e) {
                SecurityContextHolder.clearContext();
                if (debug) {
                    log.debug("解析token = {} 失败", token, e);
                }
            } catch (AuthenticationException e) {
                SecurityContextHolder.clearContext();
                if (debug) {
                    this.logger.debug("Authentication request for failed: " + e.getMessage());
                }
            }

        }
        filterChain.doFilter(request, response);


    }
}
