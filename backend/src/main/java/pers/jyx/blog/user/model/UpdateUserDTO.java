package pers.jyx.blog.user.model;


import lombok.Data;

import javax.validation.constraints.Size;

/**
 *
 * @author jyx
 */

@Data
public class UpdateUserDTO {
    @Size(min = 4, max = 18)
    private String username;
    private String password;
    @Size(min = 1, max = 12)
    private String nickname;
    @Size(min = 1, max = 8)
    private String displayName;
    private String remark;
}
