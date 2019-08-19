package cn.mbdoge.blog.model.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;

import static javax.persistence.GenerationType.IDENTITY;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "mb_user", uniqueConstraints = {@UniqueConstraint(columnNames = "username")})
@ToString
public class UserEntity implements Serializable, UserDetails {


    private static final long serialVersionUID = -1460646621318217293L;
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private long id;
    @Column(columnDefinition = "varchar(60) comment '账号'", nullable = false)
    private String username;

    @Column(columnDefinition = "varchar(100) comment '密码'", nullable = false)
//    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @JsonIgnore
    private String password;

    @Column(columnDefinition = "varchar(50) comment '昵称'", nullable = false)
    private String nickname;
    private String displayName;

    private String avatar;
    private String url;
    private String email;
    private int status;


    private Date createdAt;
    private Date updatedAt;
    @JsonIgnore
    private String remark;

    private Date nextExpireTime;
    @JsonIgnore
    private long expire = -1;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonExpired() {
        // 账户是否过期

        if (this.nextExpireTime == null) {
            return false;
        }

        Date currentDate = new Date();
        Date expireDate = this.nextExpireTime;
        return currentDate.before(expireDate);
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isEnabled() {
        return true;
    }
}
