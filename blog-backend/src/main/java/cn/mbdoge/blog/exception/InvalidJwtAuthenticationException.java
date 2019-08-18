package cn.mbdoge.blog.exception;

/**
 * @author jyx
 */
public class InvalidJwtAuthenticationException extends RuntimeException {

    public InvalidJwtAuthenticationException(String message, Throwable cause) {
        super(message, cause);
    }
}
