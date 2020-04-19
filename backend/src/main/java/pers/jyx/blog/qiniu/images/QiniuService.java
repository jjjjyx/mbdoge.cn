package pers.jyx.blog.qiniu.images;

import cn.mbdoge.jyx.exception.LocalServiceException;
import com.qiniu.common.QiniuException;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.model.FileListing;
import com.qiniu.util.Auth;
import com.qiniu.util.StringMap;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
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
    private final BucketManager bucketManager;
    // mbdoge.web.security.jwt.expiration
    // @Value("${mbdoge.web.security.jwt.expiration}")


    public QiniuService(MiscProperties miscProperties) {
        this.qiniuConfig = miscProperties.getQiniu();
        this.auth = Auth.create(qiniuConfig.getAccessKey(), qiniuConfig.getSecretKey());

        this.putPolicy = new StringMap();
        Configuration cfg = new Configuration(Region.region0());
        bucketManager = new BucketManager(auth, cfg);

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
        // String key = String.join("/", Arrays.asList(space, "$(year)", "$(mon)", "$(day)", "$(etag)$(ext)"));
        String key = space + "/$(etag)$(ext)";
        putPolicy.put("saveKey", key);
        return auth.uploadToken(qiniuConfig.getBucket(), null, expireSeconds, putPolicy);
    }

    public boolean authenticationQiniuToken(HttpServletRequest request) {
        // request.get
        // if ()
        // auth.isValidCallback();
        return false;
    }

    public FileListing queryImages(Pageable pageable, ImagesQueryCriteriaDTO criteria) {
        // FileInfo
        String bucket = qiniuConfig.getBucket();
        String space = criteria.getSpace();
        String marker = criteria.getMarker();
        int pageSize = pageable.getPageSize();

        // 最后这个参数 很奇怪啊
        try {
            return bucketManager.listFilesV2(bucket, space, marker, pageSize, "");
        } catch (Exception e) {
            log.warn("获取图片列表失败",e);
            throw new LocalServiceException("images.query.fail");
        }
    }
}
