package cn.mbdoge.blog.exception;

/**
 * 服务器连接异常
 * @author jyx
 */
public class ConnectionServerException extends Exception{
    public ConnectionServerException(String message) {
        super(message);
    }

    public ConnectionServerException(String message, Throwable cause) {
        super(message, cause);
    }
}
