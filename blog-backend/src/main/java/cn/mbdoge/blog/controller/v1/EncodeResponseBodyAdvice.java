package cn.mbdoge.blog.controller.v1;

import cn.mbdoge.blog.AppConfig;
import cn.mbdoge.blog.utils.AesEncryptUtils;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author jyx
 */
@ControllerAdvice(basePackages = "cn.mbdoge.blog.controller.v1")
@Slf4j
public class EncodeResponseBodyAdvice implements ResponseBodyAdvice {

    private final ObjectMapper objectMapper;

    @Value("${api.encrypt}")
    private boolean apiEncrypt = false;

    private AntPathMatcher pathMatcher = new AntPathMatcher();
    public EncodeResponseBodyAdvice(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public boolean supports(MethodParameter returnType, Class converterType) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class selectedConverterType, ServerHttpRequest srequest, ServerHttpResponse response) {
//        ServletServerHttpResponse resp = (ServletServerHttpResponse) response;
//        System.out.println("response.getHeaders() = " + resp.getHeaders());
//        System.out.println("resp.getHeaderNames() = " + resp.getServletResponse().getHeaders("Set-Cookie"));
        if (!apiEncrypt) {
            if (body instanceof String) {
                return "\"" + body + "\"";
            }
            return body;
        }

        ServletServerHttpRequest temp = (ServletServerHttpRequest) srequest;
        HttpServletRequest req = temp.getServletRequest();

//        if (pathMatcher.match(AppConfig.ADMIN_SERVLET_URL_MATCH, req.getServletPath())){
//            return body;
//        }

        log.trace("对方法 api = {}返回数据进行加密", req.getServletPath());
        try {
            if (selectedContentType.equals(MediaType.APPLICATION_JSON)) {
                return AesEncryptUtils.encrypt(objectMapper.writeValueAsString(body));
            } else {
                return body;
            }
        } catch (Exception e) {
            log.trace("body = {}", body);
            log.trace("对方法 api = {}返回数据进行加密失败", req.getServletPath(), e);
            return "";
        }

    }
}
