package pers.jyx.blog.article.model;


import cn.mbdoge.jyx.jwt.core.DataView;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Date;

import static javax.persistence.GenerationType.IDENTITY;
import static pers.jyx.blog.Constant.DOMAIN_NAME;

/**
 * 分类没有区分用户
 */
@Getter
@Setter
@Entity
@Table(name = DOMAIN_NAME + "_category", uniqueConstraints = {
        @UniqueConstraint(columnNames = "name")
})
public class CategoryDO implements Serializable {

    @Id
    @GeneratedValue(generator = "idGenerator")
    @GenericGenerator(name = "idGenerator", strategy = "pers.jyx.blog.basic.model.IdGenerator")
    private String id;
    private String name;
    private String icon;
    private String description;

}
