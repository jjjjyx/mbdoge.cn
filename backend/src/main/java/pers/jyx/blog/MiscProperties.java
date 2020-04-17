package pers.jyx.blog;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties("misc")
public class MiscProperties {
    private int userVer;
    private int postVer;
    private String authCookieKey;

    private Qiniu qiniu = new Qiniu();

    @Getter
    @Setter
    public static class Qiniu {
        private String accessKey = "access key";
        private String secretKey = "secret key";
        private String bucket = "bucket name";
        private String callbackUrl = "";
    }
}
