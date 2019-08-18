package cn.mbdoge.blog;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.Collections;

/**
 * @author jyx
 */
public class AppConfig {
    /**
     * 设备过期时间为 一年 1000 * 60 * 60 * 24 * 365;
     */
    public static final long DEVICE_EXPIRE = 0x757b12c00L;

    public static final String API_SERVLET_URL_PREFIX = "/api/v2";
    public static final String API_SERVLET_URL_MATCH = "/api/v2/**";
    public static final Collection<? extends GrantedAuthority> DEVICE_AUTHORITIES = Collections.singletonList(new SimpleGrantedAuthority("ROLE_DEVICE"));

    // public static final String ANTL_CORE_PATHS = "./antl-code";
}
