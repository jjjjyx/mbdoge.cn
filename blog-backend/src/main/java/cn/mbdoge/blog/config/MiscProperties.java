package cn.mbdoge.blog.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author jyx
 */
@ConfigurationProperties(prefix = "misc")
@Setter
@Getter
public class MiscProperties {

}
