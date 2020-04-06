package pers.jyx.blog.config;

import cn.mbdoge.jyx.security.CustomDaoAuthenticationProvider;
import lombok.extern.slf4j.Slf4j;
import cn.mbdoge.jyx.security.ConfigureHttpSecurity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.core.Ordered;
import org.springframework.http.HttpMethod;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import pers.jyx.blog.user.model.UserRepository;
import pers.jyx.blog.user.server.UserDetailsServiceImpl;


/**
 * @author jyx
 */
@Slf4j
@Configuration

public class WebSecurityConfig {

    @Autowired
    private UserRepository userRepository;

    @Bean
    public ConfigureHttpSecurity configureHttpSecurity() {

        return (httpSecurity) -> {
            System.out.println("httpSecurity = " + httpSecurity);
            httpSecurity.authorizeRequests()
                    .antMatchers(HttpMethod.GET, "/", "/*.html", "/favicon.ico", "/**/*.html", "/**/*.css", "/**/*.js").permitAll()
                    .antMatchers(HttpMethod.POST, "/api/v2/auth").permitAll()
                    .anyRequest()
                    .authenticated();
        };
    }

//    @Bean(name = "bearerAuthenticationFilterAdapter")
//    public BearerAuthenticationFilterAdapter bearerAuthenticationFilterAdapter (
//            JwtTokenProvider jwtTokenProvider,
//            AuthenticationEntryPoint authenticationEntryPoint) {
//        return new BearerAuthenticationFilterAdapter(jwtTokenProvider, authenticationEntryPoint) {
//            @Override
//            public String getToken(HttpServletRequest request) {
//                if (HttpUtils.isWs(request)) {
//                    return request.getParameter("token");
//                }
//                return super.getToken(request);
//            }
//        };
//    }

    @Bean("userDetailsServiceImpl")
    public UserDetailsService userDetailsService() {
        return new UserDetailsServiceImpl(userRepository);
    }

}
