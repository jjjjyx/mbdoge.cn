package cn.mbdoge.blog.config;


import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.util.StringUtils;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * @author jyx
 */
@Configuration
public class GlobalConfig implements WebMvcConfigurer {


    @Bean
    public MessageSource messageSource() {
        Locale.setDefault(Locale.SIMPLIFIED_CHINESE);
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasenames("classpath:org/springframework/security/messages", "classpath:i18n/messages");
        // \org\springframework\SECURITY\
        // reload messages every 10 seconds
        messageSource.setCacheSeconds(10);
        messageSource.setDefaultEncoding("utf-8");
        return messageSource;
    }


    @Bean
    public FilterRegistrationBean<CustomParamFilter> responseHeaderFilter() {
        FilterRegistrationBean<CustomParamFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new CustomParamFilter());
        registrationBean.addUrlPatterns("/_0/*");
        registrationBean.setOrder(-5454135);
        return registrationBean;
    }


    @Bean
    public MessageSourceAccessor messageSourceAccessor() {
        return new MessageSourceAccessor(messageSource());
    }

    @Bean
    public LocaleResolver localeResolver() {
        return new SmartLocaleResolver();
    }

    private List<Locale> LOCALES = Arrays.asList(
            Locale.US,
            Locale.SIMPLIFIED_CHINESE,
            Locale.TRADITIONAL_CHINESE,
            Locale.JAPANESE);

    private class SmartLocaleResolver extends AcceptHeaderLocaleResolver {
        @Override
        public Locale resolveLocale(HttpServletRequest request) {
            if (StringUtils.isEmpty(request.getHeader("Accept-Language"))) {
                return super.resolveLocale(request);
            }
            // 转换一下其他语言到仅提供的语言
            // @see https://stackoverflow.com/questions/36655104/spring-boot-localization-issue-accept-language-header
            List<Locale.LanguageRange> list = Locale.LanguageRange.parse(request.getHeader("Accept-Language"));
            Locale locale = Locale.lookup(list, LOCALES);
            return locale;
        }
    }


    @Bean
    public LocalValidatorFactoryBean localValidator() {
        LocalValidatorFactoryBean bean = new LocalValidatorFactoryBean();
        bean.setValidationMessageSource(messageSource());
        Map<String, String> map = new HashMap<>();
        // 快速失效， 当验证多个字段时，第一个错误字段出现，后续的不在验证
        map.put("hibernate.validator.fail_fast", "true");
        bean.setValidationPropertyMap(map);
        return bean;
    }
}
