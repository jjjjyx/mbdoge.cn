package cn.mbdoge.blog.model.pojo;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * @author jyx
 */
@Getter
@Setter
public class RespResult<T> {
    static final int SUCCESS = 0;
    static final int ERROR = 1;
    static final int WARNING = 2;
    static final int INFO = 3;


    public static RespResult success() {
        return new RespResult(SUCCESS);
    }


    public static RespResult info(String msg) {
        return new RespResult(INFO, msg);
    }

    public static RespResult error(String msg) {
        return new RespResult(ERROR, msg);
    }

    public static RespResult warning(String msg) {
        return new RespResult(WARNING, msg);
    }

    public static <T> RespResult<T> success(T data) {
        return new RespResult<>(SUCCESS, "", data);
    }

    public static <T> RespResult<T> success(String msg, T data) {
        return new RespResult<>(SUCCESS, msg, data);
    }

    public static <T> RespResult<T> info(String msg, T data) {
        return new RespResult<>(INFO, msg, data);
    }

    public static <T> RespResult<T> error(String msg, T data) {
        return new RespResult<>(ERROR, msg, data);
    }

    public static <T> RespResult<T> warning(String msg, T data) {
        return new RespResult<>(WARNING, msg, data);
    }

    @JsonView(value = DataView.BaseView.class)
    private String msg;
    @JsonView(value = DataView.BaseView.class)
    private int level;
    @JsonView(value = DataView.BaseView.class)
    private T data;
    @JsonView(value = DataView.BaseView.class)
    private Date timestamp;

    private RespResult(int level) {
        this(level, "");
    }

    private RespResult(int level, String msg) {
        this(level, msg, null);
    }

    private RespResult(int code, String msg, T data) {
        this.level = code;
        this.msg = msg;
        this.data = data;
        this.timestamp = new Date();
    }

}
