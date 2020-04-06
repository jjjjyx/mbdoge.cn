package pers.jyx.blog.user.model;

import cn.mbdoge.jyx.jwt.User;
import lombok.Getter;
import lombok.Setter;


/**
 * @author jyx
 */

public class OnlineUserVO extends User {

    private static final long serialVersionUID = 982649350878695388L;

    @Getter
    @Setter
    private UserDetailDO detail;

}
