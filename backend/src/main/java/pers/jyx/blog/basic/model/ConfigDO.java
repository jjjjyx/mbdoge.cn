package pers.jyx.blog.basic.model;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
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
@Table(name = DOMAIN_NAME + "_config")
@ToString
public class ConfigDO implements Serializable {

    private static final long serialVersionUID = 5856427953410232167L;

    @Id
    private String name;
    @Column(name = "value", nullable = true, length = 10000)
    private String value;
    private String text;

}
