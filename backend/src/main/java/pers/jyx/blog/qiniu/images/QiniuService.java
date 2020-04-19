package pers.jyx.blog.qiniu.images;

import com.qiniu.util.Auth;
import com.qiniu.util.StringMap;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pers.jyx.blog.MiscProperties;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

@Slf4j
@Service
public class QiniuService {

    private final MiscProperties.Qiniu qiniuConfig;
    private final Auth auth;
    private final StringMap putPolicy;
    // mbdoge.web.security.jwt.expiration
    // @Value("${mbdoge.web.security.jwt.expiration}")


    public QiniuService(MiscProperties miscProperties) {
        this.qiniuConfig = miscProperties.getQiniu();
        this.auth = Auth.create(qiniuConfig.getAccessKey(), qiniuConfig.getSecretKey());

        this.putPolicy = new StringMap();

        String returnBody = "{\"url\": \""+qiniuConfig.getDomain()+"$(key)\",\"key\":$(key), \"hash\":$(etag), \"size\":$(fsize), \"bucket\":\"$(bucket)\", \"name\":\"$(fname)\", \"info\":$(imageInfo), \"imageAve\":$(imageAve), \"exif\": $(exif), \"mimeType\": $(mimeType), \"ext\": $(ext), \"uuid\": $(uuid), \"space\": $(x:space),\"remark\": $(x:remark)}";
        putPolicy.put("returnBody", returnBody);
        if (StringUtils.isNotEmpty(qiniuConfig.getCallbackUrl())) {
            putPolicy.put("callbackBody", returnBody);
            putPolicy.put("callbackUrl", qiniuConfig.getCallbackUrl());
            putPolicy.put("callbackBodyType", "application/json");
        }

    }

    public String getTokenBySpace(String space) {
        // 固定文件格式为
        // 文件区域/yyyy/MM/dd/hash.ext
        /* 1分钟过期 */
        long expireSeconds = 60;
        String key = String.join("/", Arrays.asList(space, "$(year)", "$(mon)", "$(day)", "$(etag)$(ext)"));
        log.debug("upload key = {}", key);
        putPolicy.put("saveKey", key);
        return auth.uploadToken(qiniuConfig.getBucket(), null, expireSeconds, putPolicy);
    }

    public boolean authenticationQiniuToken(HttpServletRequest request) {
        // request.get
        // if ()
        // auth.isValidCallback();
        return false;
    }

    public Page queryImages(Pageable pageable, ImagesQueryCriteriaDTO criteria) {

        return null;
    }
}
