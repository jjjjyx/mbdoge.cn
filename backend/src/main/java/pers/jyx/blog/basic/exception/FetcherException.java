package pers.jyx.blog.basic.exception;



import cn.mbdoge.jyx.web.model.RespResult;
import lombok.Getter;
import org.apache.http.HttpResponse;

import java.io.IOException;

public class FetcherException extends IOException {
    private HttpResponse response;
    @Getter
    private RespResult<Object> data;

    public FetcherException(HttpResponse response, RespResult<Object> data) {
        super(data.getMsg());
        this.data = data;
        this.response = response;

    }
}