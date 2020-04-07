package pers.jyx.blog.user.model;

import cn.mbdoge.jyx.jwt.core.DataView;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.*;


import javax.persistence.*;
import java.io.Serializable;

import java.util.Date;
import java.util.List;
import java.util.Objects;

import static javax.persistence.GenerationType.IDENTITY;
import static pers.jyx.blog.Constant.DOMAIN_NAME;

@Getter
@Setter
@Entity
@Table(name = DOMAIN_NAME + "_user", uniqueConstraints = {@UniqueConstraint(columnNames = "username")})
public class UserDO implements Serializable {

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = new Date();
    }

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    @Column(columnDefinition = "varchar(60) comment '账号'", nullable = false)
    private String username;

    @Column(columnDefinition = "varchar(100) comment '密码'", nullable = false)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;
    @Column(columnDefinition = "varchar(50) comment '昵称'", nullable = false)
    private String nickname;

    @JsonView(DataView.AdminView.class)
    private Integer userStatus;
    private String displayName;

    @JsonView(DataView.AdminView.class)
    private Date createdAt;
    @JsonView(DataView.AdminView.class)
    private Date updatedAt;

    @ManyToMany(cascade = {CascadeType.REFRESH}, fetch = FetchType.EAGER)
    @JoinTable( uniqueConstraints = {@UniqueConstraint(columnNames = {"userdo_id", "roles_id"})})
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private List<RoleDO> roles;

    @JsonView(DataView.AdminView.class)
    private String remark;
    private Date nextExpireTime;

    /**
     * 用户的信息版本，兼容老版本用
     */
    @JsonView(DataView.AdminView.class)
    private Integer ver;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonManagedReference
    private UserDetailDO userDetail;



    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        UserDO userDO = (UserDO) o;
        return Objects.equals(id, userDO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }


}
