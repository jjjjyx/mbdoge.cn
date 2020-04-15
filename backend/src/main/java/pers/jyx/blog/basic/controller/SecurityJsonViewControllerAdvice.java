package pers.jyx.blog.basic.controller;


import cn.mbdoge.jyx.jwt.SecurityUtils;
import cn.mbdoge.jyx.jwt.core.DataView;
import org.apache.el.stream.Stream;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.AbstractMappingJacksonResponseBodyAdvice;
import pers.jyx.blog.user.model.UserRole;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestControllerAdvice
public class SecurityJsonViewControllerAdvice extends AbstractMappingJacksonResponseBodyAdvice {

    public static final Map<String, Class> MAPPING = new HashMap<>();

    static {
        /**
         * 可看到字段分为管理员和普通用户看到字段
         * 有后续需求在加入
          */
        MAPPING.put(UserRole.ADMIN, DataView.AdminView.class);
        MAPPING.put(UserRole.USER, DataView.UserView.class);
        MAPPING.put(UserRole.GUEST, DataView.Anonymous.class);
    }

    @Override
    protected void beforeBodyWriteInternal(MappingJacksonValue bodyContainer, MediaType contentType, MethodParameter returnType, ServerHttpRequest request, ServerHttpResponse response) {
        // List<String> roles = SecurityUtils.getCurrentUserRoleList();
        // System.out.println("roles = " + roles);
        Collection<? extends GrantedAuthority> authorities = SecurityUtils.getUsernameAuthorities();
        Class<?> viewClass = authorities.stream().findFirst()
                .flatMap((o) -> Optional.ofNullable(MAPPING.get(o.getAuthority())))
                .orElse(DataView.Anonymous.class);


        bodyContainer.setSerializationView(viewClass);
    }
}
