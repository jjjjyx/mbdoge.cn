package pers.jyx.blog;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties("misc")
public class MiscProperties {
    private int userVer;
}
