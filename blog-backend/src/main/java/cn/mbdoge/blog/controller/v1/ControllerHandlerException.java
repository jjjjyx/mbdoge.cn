package cn.mbdoge.blog.controller.v1;


import cn.mbdoge.blog.exception.LocalServiceException;
import cn.mbdoge.blog.model.pojo.RespResult;
import cn.mbdoge.blog.utils.Utils;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.service.spi.ServiceException;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;
import java.util.Objects;
import java.util.Set;


/**
 * @author jyx
 */
@ControllerAdvice
@ResponseBody
@Slf4j
public class ControllerHandlerException {
    private final MessageSourceAccessor messageSourceAccessor;

    public ControllerHandlerException(MessageSourceAccessor messageSourceAccessor) {
        this.messageSourceAccessor = messageSourceAccessor;
    }


    /**
     * 400 - Bad Request
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public RespResult handleMissingServletRequestParameterException(MissingServletRequestParameterException e) {
        log.trace("缺少请求参数", e);
        return RespResult.info(messageSourceAccessor.getMessage("controller.parameter.MissingServletRequest", new Object[]{e.getParameterName()}));
    }

    /**
     * 400 - Bad Request
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({HttpMessageNotReadableException.class, IllegalArgumentException.class})
    public RespResult handleHttpMessageNotReadableException(RuntimeException e) {
        log.trace("参数解析失败", e);
        e.printStackTrace();
        return RespResult.info(messageSourceAccessor.getMessage("controller.parameter.HttpMessageNotReadable"));
    }

    /**
     * 400 - Bad Request
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public RespResult handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.trace("参数验证失败", e);
        BindingResult result = e.getBindingResult();

        FieldError error = result.getFieldError();
        String field = Objects.requireNonNull(error).getField();

        return RespResult.info(messageSourceAccessor.getMessage("controller.parameter.MethodArgumentNotValid", new Object[]{field}));
    }

    /**
     * 400 - Bad Request
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BindException.class)
    public RespResult handleBindException(BindException e) {
        // 参数不合法包括了 绑定失败， 错误的类型提交， 比如bool 接收string 以及自定义验证规则错误
        log.trace("参数不合法", e);
        ObjectError error = e.getAllErrors().get(0);
        String name = error.getDefaultMessage();
        Object data = null;

        if (error instanceof FieldError) {
            //            提交了不类型不正确的参数
            FieldError fieldError = (FieldError) error;
            data = fieldError.getField();
            name = data + " param error";
        }
        return RespResult.info(name, data);
    }

    /**
     * 400 - Bad Request
     * https://stackoverflow.com/questions/37471467/exception-handling-in-spring-boot
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public RespResult handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex) {
        log.trace("参数不合法", ex);
        String name = ex.getName();
        String type = Objects.requireNonNull(ex.getRequiredType()).getSimpleName();
        Object value = ex.getValue();

        return RespResult.info(messageSourceAccessor.getMessage("controller.parameter.ArgumentTypeMismatch", new Object[]{name, type, value}));
    }


    /**
     * 400 - Bad Request
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
    public RespResult handleServiceException(ConstraintViolationException e) {
        log.trace("参数验证失败", e);
        Set<ConstraintViolation<?>> violations = e.getConstraintViolations();
        ConstraintViolation<?> violation = violations.iterator().next();
        String message = violation.getMessage();
        return RespResult.info(messageSourceAccessor.getMessage("controller.parameter.ConstraintViolation", new Object[]{message}));
    }

    /**
     * 400 - Bad Request
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ValidationException.class)
    public RespResult handleValidationException(ValidationException e) {
        log.trace("参数验证失败", e);

        return RespResult.info(messageSourceAccessor.getMessage("controller.parameter.Validation"));
    }
    /**
     * 400 - Bad Request
     */
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(AccessDeniedException.class)
    public RespResult handleAccessDeniedException(AccessDeniedException e) {
        return RespResult.info(messageSourceAccessor.getMessage("auth.access.denied"));
    }


    /**
     * 404 - Not Found
     */
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoHandlerFoundException.class)
    public RespResult noHandlerFoundException(NoHandlerFoundException e) {
        log.trace("Not Found", e);

        return RespResult.info(messageSourceAccessor.getMessage("controller.404"));
    }


    /**
     * 405 - Method Not Allowed
     */
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public RespResult handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        log.trace("不支持当前请求方法", e);
        return RespResult.info(messageSourceAccessor.getMessage("controller.notMethod"));
    }

    /**
     * 415 - Unsupported Media Type
     */
    @ResponseStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public RespResult handleHttpMediaTypeNotSupportedException(HttpMediaTypeNotSupportedException e) {
        log.trace("不支持当前媒体类型", e);
        return RespResult.info(messageSourceAccessor.getMessage("controller.notMediaType"));
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({LocalServiceException.class})
    public RespResult handleServiceException(LocalServiceException e) {
        return RespResult.warning(messageSourceAccessor.getMessage(e.getMessage()), e.getData());
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler({AuthenticationException.class})
    public RespResult handleServiceException(AuthenticationException e) {
        return RespResult.warning(e.getMessage());
    }

    /**
     *  https://jira.spring.io/browse/SPR-14651
     *  文件上传 大小超出
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({MultipartException.class})
    public RespResult handleMultipartException(MultipartException e) {
        log.trace("上传文件失败", e);
        String message = "upload.fail";
        if (e instanceof MaxUploadSizeExceededException) {
            message = "upload.fail.exceeded.max.size";
        }
        return RespResult.warning(messageSourceAccessor.getMessage(message));
    }




    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({ServiceException.class, UsernameNotFoundException.class})
    public RespResult handleServiceException(RuntimeException e) {
        log.warn("业务逻辑异常", e);
        return RespResult.warning(e.getMessage());
    }




    /**
     * 操作数据或库出现异常 重复的键
     */
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(DataIntegrityViolationException.class)
    public RespResult handleException(DataIntegrityViolationException e) {

        Throwable cause = Utils.getCause(e);
        // todo DataIntegrityViolationException 并不是针对重复建
        String msg = cause.getMessage();
        int index = msg.indexOf("for key");
        if (index > 0) {
            msg = msg.substring(0, index);
        }

        return RespResult.error(msg);
    }

    /**
     * 操作数据或库出现异常
     */
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(DataAccessException.class)
    public RespResult handleException(DataAccessException e) {
        log.error("操作数据库出现异常:", e);

        Throwable cause = Utils.getCause(e);

        return RespResult.error(messageSourceAccessor.getMessage("controller.500") + " by " + cause.getClass().getSimpleName());
    }

    /**
     * 500 - Internal Server Error
     */
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public RespResult handleException(Exception e) {
        log.error("通用异常", e);
        return RespResult.error(messageSourceAccessor.getMessage("controller.500"));

    }

}
