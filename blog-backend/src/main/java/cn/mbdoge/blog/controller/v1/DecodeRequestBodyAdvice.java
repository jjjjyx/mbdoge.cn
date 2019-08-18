package cn.mbdoge.blog.controller.v1;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdvice;

import java.io.IOException;
import java.lang.reflect.Type;

@ControllerAdvice(basePackages = "com.sgk.search.controller.v1")
@Slf4j
public class DecodeRequestBodyAdvice implements RequestBodyAdvice {
    // 仅对json 提交数据起效 并且 注解了 @RequestBody
    @Override
    public boolean supports(MethodParameter methodParameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }

    @Override
    public HttpInputMessage beforeBodyRead(HttpInputMessage inputMessage, MethodParameter parameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) throws IOException {
        log.debug("DecodeRequestBodyAdvice beforeBodyRead");
//        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputMessage.getBody()));
//        String line;
//        while ((line = bufferedReader.readLine()) != null) {
//            System.out.println("line = " + line);
//        }
//        String content = IOUtils.toString(inputMessage.getBody(), "UTF-8");

        return inputMessage;
    }

    @Override
    public Object afterBodyRead(Object body, HttpInputMessage inputMessage, MethodParameter parameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
        log.debug("DecodeRequestBodyAdvice afterBodyRead");
        return body;
    }

    @Override
    public Object handleEmptyBody(Object body, HttpInputMessage inputMessage, MethodParameter parameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
        log.debug("handleEmptyBody body = {} body.getClass = {}", body, body.getClass());
        return body;
    }
}
