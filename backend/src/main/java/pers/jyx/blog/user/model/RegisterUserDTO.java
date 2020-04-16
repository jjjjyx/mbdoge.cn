package pers.jyx.blog.user.model;


import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

/**
 *
 * @author jyx
 */
@Getter
@Setter
public class RegisterUserDTO {

    @NotBlank(message = "{auth.register.fail.username.not-blank}")
    @Size(min = 4, max = 18)
    private String username;
    @NotBlank(message = "{auth.register.fail.password.not-blank}")
    private String password;
    @Size(min = 1, max = 12)
    private String nickname;
    @Size(min = 1, max = 8)
    private String displayName;

    private String avatar;
    private String email;
    private String url;

    private String remark;
    @Min(1000)
    private Long expire;


    @NotNull
    @Size(min = 1)
    private List<String> roles;

}
