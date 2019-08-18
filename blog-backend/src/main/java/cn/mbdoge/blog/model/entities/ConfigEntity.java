package cn.mbdoge.blog.model.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @author jyx
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "mb_config")
public class ConfigEntity implements Serializable {

    private static final long serialVersionUID = 5856427953410232167L;

    @Id
    private String name;
    @Column(name = "value", nullable = true, length = 10000)
    private String value;
    private String text;

}
