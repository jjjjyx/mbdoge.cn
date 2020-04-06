package pers.jyx.blog.user.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import static javax.persistence.GenerationType.IDENTITY;
import static pers.jyx.blog.Constant.DOMAIN_NAME;

/**
 * @author jyx
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = DOMAIN_NAME + "_menu", uniqueConstraints = {
        @UniqueConstraint(columnNames = "name")
})
@ToString(exclude = "children")
public class MenuDO {

    public MenuDO(MenuDO menuDO) {
        this.id = menuDO.id;
        this.path = menuDO.path;
        this.redirect = menuDO.redirect;
        this.component = menuDO.component;
        this.name = menuDO.name;
        this.iconCls = menuDO.iconCls;
        this.orderNum = menuDO.orderNum;
        this.menuType = menuDO.menuType;
        this.hidden = menuDO.hidden;
        this.isFrame = menuDO.isFrame;
        this.createdAt = menuDO.createdAt;
        this.updatedAt = menuDO.updatedAt;
        this.parent = menuDO.parent;
        // this.children = menuDO.children;
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = new Date();
    }

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    private String path;
    private String redirect;
    private String component;
    private String name;


    @Column(columnDefinition = "INT(11) DEFAULT 1 NULL")
    private Integer orderNum;

    private Type menuType;
    private Boolean hidden;
    private Boolean alwaysShow;

    /**
     * 是否外链
     */
    private Boolean isFrame;
    /**
     * meta 信息
     */
    private Boolean affix;
    private Boolean breadcrumb;
    private String iconCls;
    private String title;

    private Date createdAt;
    private Date updatedAt;

    @ManyToOne
    @JsonIgnore
    private MenuDO parent;

    public Long getParentId() {
        return this.parent == null ? null : this.parent.getId();
    }

    @OneToMany(mappedBy = "parent")
    @JsonIgnore
    private List<MenuDO> children;

    public enum Type {
        /**
         * 菜单类型
         */
        menu,
        /**
         * 按钮类型
         */
        button
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        MenuDO menuDO = (MenuDO) o;
        return Objects.equals(id, menuDO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
