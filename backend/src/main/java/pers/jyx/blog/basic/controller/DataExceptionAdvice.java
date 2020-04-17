package pers.jyx.blog.basic.controller;

import cn.mbdoge.jyx.exception.ExceptionUtils;
import cn.mbdoge.jyx.web.model.RespResult;
import org.apache.commons.lang3.ThreadUtils;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;


@ControllerAdvice
@ResponseBody
@Order(Ordered.HIGHEST_PRECEDENCE)
public class DataExceptionAdvice {

    protected final MessageSourceAccessor messageSourceAccessor;

    public DataExceptionAdvice(MessageSourceAccessor messageSourceAccessor) {
        this.messageSourceAccessor = messageSourceAccessor;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({DataIntegrityViolationException.class})
    public RespResult<?> handleAccessDeniedException(DataIntegrityViolationException e) {
//        ConstraintViolationException
        Throwable cause = ExceptionUtils.getCause(e);
        String message = cause.getMessage();
        String suffix = "";
        if (message.contains("Duplicate")) {
            suffix = ".duplicate";
        }

        return RespResult.warning(messageSourceAccessor.getMessage("DataIntegrityViolationException.message" + suffix, "操作失败"));
    }
}
