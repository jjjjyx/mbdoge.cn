package pers.jyx.blog.qiniu.images;

import cn.mbdoge.jyx.exception.LocalServiceException;
import com.qiniu.common.QiniuException;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.model.BatchStatus;
import com.qiniu.storage.model.FileInfo;
import com.qiniu.storage.model.FileListing;
import com.qiniu.util.Auth;
import com.qiniu.util.StringMap;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.methods.HttpGet;
import org.springframework.data.domain.Pageable;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Service;
import pers.jyx.blog.MiscProperties;
import pers.jyx.blog.basic.Api;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

@Slf4j
@Service
public class QiniuService {

    private static final String IMAGE_INFO_CACHE_KEY = "image_info_cache_key";
    private final MiscProperties.Qiniu qiniuConfig;
    private final Auth auth;
    private final StringMap putPolicy;
    private final BucketManager bucketManager;
    private final RedisTemplate<String, ImageInfoVO> redisTemplate;
    private final ExecutorService threadPoolExecutor;
    // mbdoge.web.security.jwt.expiration
    // @Value("${mbdoge.web.security.jwt.expiration}")


    public QiniuService(MiscProperties miscProperties, RedisConnectionFactory redisConnectionFactory) {
        this.qiniuConfig = miscProperties.getQiniu();
        this.auth = Auth.create(qiniuConfig.getAccessKey(), qiniuConfig.getSecretKey());

        this.putPolicy = new StringMap();
        Configuration cfg = new Configuration(Region.region0());
        bucketManager = new BucketManager(auth, cfg);

        String returnBody = "{\"url\": \"" + qiniuConfig.getDomain() + "$(key)\",\"key\":$(key), \"hash\":$(etag), \"size\":$(fsize), \"bucket\":\"$(bucket)\", \"name\":\"$(fname)\", \"info\":$(imageInfo), \"imageAve\":$(imageAve), \"exif\": $(exif), \"mimeType\": $(mimeType), \"ext\": $(ext), \"uuid\": $(uuid), \"space\": $(x:space),\"remark\": $(x:remark)}";
        putPolicy.put("returnBody", returnBody);
        if (StringUtils.isNotEmpty(qiniuConfig.getCallbackUrl())) {
            putPolicy.put("callbackBody", returnBody);
            putPolicy.put("callbackUrl", qiniuConfig.getCallbackUrl());
            putPolicy.put("callbackBodyType", "application/json");
        }

        RedisTemplate<String, ImageInfoVO> redisTemplate = new RedisTemplate<>();
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        redisTemplate.setKeySerializer(stringRedisSerializer);
        redisTemplate.setHashKeySerializer(stringRedisSerializer);
        redisTemplate.afterPropertiesSet();

        this.redisTemplate = redisTemplate;

        this.threadPoolExecutor = Executors.newSingleThreadExecutor();
    }

    public String getTokenBySpace(String space) {
        // 固定文件格式为
        // 文件区域/yyyy/MM/dd/hash.ext
        /* 1分钟过期 */
        long expireSeconds = 60;
        // String key = String.join("/", Arrays.asList(space, "$(year)", "$(mon)", "$(day)", "$(etag)$(ext)"));
        String key = space + "/$(etag)$(ext)";
        // $(imageInfo.width)x$(imageInfo.height)_
        putPolicy.put("saveKey", key);
        // System.out.println("key = " + key);
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
            final FileListing fileListing = bucketManager.listFilesV2(bucket, space, marker, pageSize, "");
            // 缓存下高宽
            // fileListing.items
            threadPoolExecutor.submit(() -> {
                final FileInfo[] items = fileListing.items;
                HashOperations<String, String, ImageInfoVO> hashOperations = this.redisTemplate.opsForHash();

                Arrays.stream(items)
                .filter((item) -> !hashOperations.hasKey(IMAGE_INFO_CACHE_KEY, item.key))
                .parallel().forEach((item) -> {
                    String src = qiniuConfig.getDomain() + item.key;
                    ImageInfoVO info = new ImageInfoVO();
                    info.setSrc(src);
                    HttpGet get = new HttpGet(src + "?imageInfo");
                    try {
                        ImageInfoDTO dto = Api.fetch(get, ImageInfoDTO.class);
                        info.setH(dto.getHeight());
                        info.setW(dto.getWidth());
                        log.trace("{} info = {}", item.key, dto);
                        hashOperations.put(IMAGE_INFO_CACHE_KEY, item.key, info);
                    } catch (IOException e) {
                        log.info("{} 获取图片信息失败！ {}", item.key, e.getMessage());
                    }
                });
            });
            return fileListing;
        } catch (Exception e) {
            log.warn("获取图片列表失败");
            FileListing fileListing = new FileListing();

            return fileListing;
            // throw new LocalServiceException("images.query.fail");
        }
    }

    public List<ImageInfoVO> queryImagesInfo(ImagesInfoQueryCriteriaDTO dto) {
        HashOperations<String, String, ImageInfoVO> hashOperations = this.redisTemplate.opsForHash();
        List<String> keys = dto.getKeys();
        return hashOperations.multiGet(IMAGE_INFO_CACHE_KEY, keys);
    }
}
