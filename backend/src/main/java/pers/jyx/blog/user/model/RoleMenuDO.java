package pers.jyx.blog.user.model;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

import static pers.jyx.blog.Constant.DOMAIN_NAME;


/**
 * @author jyx
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = DOMAIN_NAME + "_role_menu")
@ToString(exclude = {"menu", "role"})
public class RoleMenuDO implements Serializable {


    @EmbeddedId
    private Pk id = new Pk();

    @MapsId("roleId")
    @ManyToOne
    private RoleDO role;


    @MapsId("menuId")
    @ManyToOne
    private MenuDO menu;

    /**
     * @author jyx
     */
    @Embeddable
    @Data
    public static class Pk implements Serializable {

        private static final long serialVersionUID = -6585095582309717701L;
        private Long roleId;
        private Long menuId;
    }
}
