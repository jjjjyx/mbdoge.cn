package pers.jyx.blog.user.model;

import cn.mbdoge.jyx.jwt.User;
import cn.mbdoge.jyx.jwt.core.DataView;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Getter;
import lombok.Setter;


/**
 * @author jyx
 */
@Getter
@Setter
public class OnlineUserVO extends User {

    private static final long serialVersionUID = 982649350878695388L;

    @JsonView(DataView.UserView.class)
    private String uid;

    private UserDetailDO detail;

    private String avatar;

    private String nickname;

    private String email;

    private String url;

}
