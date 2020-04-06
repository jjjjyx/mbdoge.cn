package pers.jyx.blog.user.model;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

import static javax.persistence.GenerationType.IDENTITY;
import static pers.jyx.blog.Constant.DOMAIN_NAME;

/**
 * @author jyx
 */
@Getter
@Setter
@Entity
@Table(name = DOMAIN_NAME + "_role", uniqueConstraints = {@UniqueConstraint(columnNames = "name")})
public class RoleDO implements Serializable {

    private static final long serialVersionUID = 491693510965147950L;

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    /**
     * 角色等级 // 最大值99 管理员为100
     */
    private Integer level;
    private String name;
    private String remark;
    private Date createdAt;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        RoleDO roleDO = (RoleDO) o;
        return Objects.equals(id, roleDO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
