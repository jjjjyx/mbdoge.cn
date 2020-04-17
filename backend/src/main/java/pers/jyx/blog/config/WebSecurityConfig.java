package pers.jyx.blog.config;

import cn.mbdoge.jyx.security.ConfigureWebSecurity;
import cn.mbdoge.jyx.security.CustomDaoAuthenticationProvider;
import lombok.extern.slf4j.Slf4j;
import cn.mbdoge.jyx.security.ConfigureHttpSecurity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.core.Ordered;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import pers.jyx.blog.user.model.UserRepository;
import pers.jyx.blog.user.server.UserDetailsServiceImpl;

import java.util.HashMap;
import java.util.Map;


/**
 * @author jyx
 */
@Slf4j
@Configuration

public class WebSecurityConfig {

    @Autowired
    private UserRepository userRepository;

    @Bean
    public ConfigureWebSecurity configureWebSecurity() {
        return (webSecurity) -> {
            webSecurity.ignoring()
                    .antMatchers("/swagger-ui.html")
                    .antMatchers("/webjars/**")
                    .antMatchers("/v2/**")
                    .antMatchers("/swagger-resources/**");
        };
    }

    @Bean
    public ConfigureHttpSecurity configureHttpSecurity() {

        return (httpSecurity) -> {
            httpSecurity.authorizeRequests()
                    .antMatchers(HttpMethod.GET, "/", "/*.html", "/favicon.ico", "/**/*.html", "/**/*.css", "/**/*.js").permitAll()
                    .antMatchers(HttpMethod.POST, "/api/v2/auth").permitAll()
                    .antMatchers("/swagger-resources", "/v2/**", "/webjars/**", "/swagger-ui.html").permitAll()
//                    .antMatchers("/v2/api-docs",//swagger api json
//                            "/swagger-resources/configuration/ui",//用来获取支持的动作
//                            "/swagger-resources",//用来获取api-docs的URI
//                            "/swagger-resources/configuration/security",//安全选项
//                            "/swagger-ui.html").permitAll()
                    .anyRequest()
                    .authenticated();

//            httpSecurity.ignoring()

            httpSecurity.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
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
