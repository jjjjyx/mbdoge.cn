package cn.mbdoge.blog.config;


import cn.mbdoge.blog.AppConfig;
import cn.mbdoge.blog.secruity.CustomAuthenticationFilter;
import cn.mbdoge.blog.secruity.JwtAuthenticationEntryPoint;
import cn.mbdoge.blog.secruity.JwtTokenProvider;
import cn.mbdoge.blog.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.Collections;

/**
 * @author jyx
 */
@SuppressWarnings("SpringJavaAutowiringInspection")
@Configuration
@EnableWebSecurity
@EnableAutoConfiguration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Value("${pass.secret}")
    private String passwordSecret;
    @Value("${user.auth.header}")
    private String userAuthHeaderKey;

    private final UserDetailsServiceImpl userDetailsService;
    private final JwtTokenProvider jwtTokenProvider;
    private final MessageSource messageSource;
    private final MessageSourceAccessor messageSourceAccessor;

    public WebSecurityConfig(UserDetailsServiceImpl userDetailsService, JwtTokenProvider jwtTokenProvider, MessageSource messageSource, MessageSourceAccessor messageSourceAccessor) {
        this.userDetailsService = userDetailsService;
        this.jwtTokenProvider = jwtTokenProvider;
        this.messageSource = messageSource;
        this.messageSourceAccessor = messageSourceAccessor;
    }

    @Bean
    public AuthenticationEntryPoint authenticationEntryPoint() {
        return new JwtAuthenticationEntryPoint();
    }


    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setHideUserNotFoundExceptions(false);
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        provider.setMessageSource(messageSource);
        return provider;
    }

    @Bean
    public AuthenticationManager customAuthenticationManager() throws Exception {
        return authenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new Pbkdf2PasswordEncoder(passwordSecret);
    }

    // /**
    //  * 这个方法名称 必须与类名一致，不然会造成重复调用 org.springframework.web.filter.OncePerRequestFilter#28
    //  */
    // @Bean
    // public JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter() {
    //     return new JwtAuthenticationTokenFilter(jwtTokenProvider);
    // }

    @Bean
    public CustomAuthenticationFilter customAuthenticationFilter() throws Exception {
        return new CustomAuthenticationFilter(authenticationManager(), authenticationEntryPoint(), jwtTokenProvider, messageSourceAccessor);
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        // http://localhost:9527
        configuration.setAllowedOrigins(Collections.singletonList("*"));
//        configuration.setAllowedOrigins(Collections.singletonList("http://localhost:3000"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE"));
        configuration.setAllowedHeaders(Collections.singletonList("*"));
        configuration.setAllowCredentials(true);
        configuration.setExposedHeaders(Arrays.asList("Accept-Language", userAuthHeaderKey));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration(AppConfig.API_SERVLET_URL_MATCH, configuration);

        return source;
    }

    @Override
    public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) {
        authenticationManagerBuilder.authenticationProvider(authenticationProvider());
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        String pass = passwordEncoder().encode("user1Pass");
        auth.inMemoryAuthentication()
                .withUser("user1").password(pass)
                .authorities("ROLE_NODE");
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .authorizeRequests()
                // Allow anonymous access to websocket
                .antMatchers("/ws/**").permitAll()
//                .antMatchers(HttpMethod.GET, "/", "/*.html", "/favicon.ico", "/**/*.html", "/**/*.css", "/**/*.js").permitAll()
                // 对于获取token的 rest api要允许匿名访问
                .antMatchers(HttpMethod.POST, AppConfig.API_SERVLET_URL_PREFIX + "/auth").permitAll()
                // 除上面外的所有请求全部需要鉴权认证
                .anyRequest().authenticated()
                .and()
                .exceptionHandling()
                // .authenticationEntryPoint(jwtAuthenticationEntryPoint())
                .authenticationEntryPoint(authenticationEntryPoint())
                .and()
                // .httpBasic().and()
                // 基于token，所以不需要session
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .cors().and()
                // 由于使用的是JWT，不需要csrf
                .csrf().disable()
                .logout().disable();
        // BasicAuthenticationFilter
        httpSecurity.addFilter(customAuthenticationFilter());
        httpSecurity.headers().cacheControl();

    }

}
