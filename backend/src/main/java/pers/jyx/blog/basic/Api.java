package pers.jyx.blog.basic;

import cn.mbdoge.jyx.web.model.RespResult;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import pers.jyx.blog.Constant;
import pers.jyx.blog.basic.exception.FetcherException;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class Api {
    private final static ObjectMapper objectMapper = new ObjectMapper();
    public static CloseableHttpClient getHttpClient () {
        HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
        httpClientBuilder.disableAutomaticRetries();



        httpClientBuilder.setDefaultRequestConfig(getRequestConfigByTimeout(Constant.FETCHER_TIMEOUT));
        return httpClientBuilder.build();
    }

    public static RequestConfig getRequestConfigByTimeout (int timeout) {
        RequestConfig.Builder req = RequestConfig.custom();
        // 超时时间
        req.setConnectTimeout(timeout);
        req.setConnectionRequestTimeout(timeout);
        req.setSocketTimeout(timeout);

        req.setExpectContinueEnabled(false);

        return req.build();
    }
    public static String fetch (HttpRequestBase req) throws IOException {
        HttpClient client = getHttpClient();
        HttpResponse response = client.execute(req);
        int statusCode = response.getStatusLine().getStatusCode();
        if (statusCode == HttpStatus.SC_OK) {
            return EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);
        } else {
            throwsFetcher(response);
        }
        return null;
    }

    public static <T> T fetch (HttpRequestBase req, Class<T> cls) throws IOException {
        return fetch(req, cls, getHttpClient());
    }

    public static <T> T fetch (HttpRequestBase req, Class<T> cls, HttpClient client) throws IOException {
        HttpResponse response = client.execute(req);
        int statusCode = response.getStatusLine().getStatusCode();
        if (statusCode == HttpStatus.SC_OK) {
            return objectMapper.readValue(response.getEntity().getContent(), cls);
        } else {
            throwsFetcher(response);
        }
        return null;
    }

    public static <T> T fetch (HttpRequestBase req, TypeReference<T> typeReference) throws IOException {
        return fetch(req, typeReference, getHttpClient());
    }

    public static <T> T fetch (HttpRequestBase req, TypeReference<T> typeReference, HttpClient client) throws IOException {
        HttpResponse response = client.execute(req);
        int statusCode = response.getStatusLine().getStatusCode();
        if (statusCode == HttpStatus.SC_OK) {
            return objectMapper.readValue(response.getEntity().getContent(), typeReference);
        } else {
            throwsFetcher(response);
        }
        return null;
    }
    private static void throwsFetcher (HttpResponse response) throws IOException {

        RespResult<Object> ret = objectMapper.readValue(response.getEntity().getContent(), new TypeReference<RespResult<Object>>() {
        });
        throw new FetcherException(response, ret);

//            DialResp<String> stringDialResp = new DialResp<>();
//            stringDialResp.setCode(-1);
//            stringDialResp.setData("请求失败!!");
//            e.printStackTrace();
//            throw new FetcherException(response, stringDialResp);


    }

}
