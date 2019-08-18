package cn.mbdoge.blog.exception;

import lombok.Getter;

/**
 * @author jyx
 */

public class LocalServiceException extends ServiceException {
    @Getter
    private Object data;
    public LocalServiceException(String message) {
        super(message);
    }

    public LocalServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public LocalServiceException(String message, Object data) {
        super(message);
        this.data = data;
    }

    public LocalServiceException(String message, Object data, Throwable cause) {
        super(message, cause);
        this.data = data;
    }
}
