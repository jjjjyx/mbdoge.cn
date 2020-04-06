package pers.jyx.blog.user.model;

/**
 * @author jyx
 */
public interface UserRole {
    /**
     * 各个角色权限等级值
     */
    int ADMIN_LEVEL = 100;
    int USER_LEVEL = 5;

    String ADMIN = "ADMIN";
    String SUPREME = "USER";

}
