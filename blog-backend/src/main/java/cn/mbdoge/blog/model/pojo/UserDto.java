package cn.mbdoge.blog.model.pojo;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

/**
 *
 * @author jyx
 */
@Getter
@Setter
public class UserDto {

    @NotEmpty(message = "{auth.register.fail.username.notNull}")
    @Size(min = 4, max = 18)
    private String username;
    @NotEmpty(message = "{auth.register.fail.password.notNull}")
    private String password;
    @Size(min = 1, max = 12)
    private String nickname;
    @Size(min = 1, max = 8)
    private String displayName;
    private String remark;
    private long expire = -1;
    private boolean official;
    private String email;
    private String url;
    private String avatar;

}
