package pers.jyx.blog.basic.model;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author jyx
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ConfigKey {
    String name();

    /**
     * 用户默认过期时间
     */
    String SETTING_USER_DEFAULT_EXPIRE = "setting.user.default.expire";

}
