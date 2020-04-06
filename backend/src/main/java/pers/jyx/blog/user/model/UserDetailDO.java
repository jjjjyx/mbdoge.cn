package pers.jyx.blog.user.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.*;
import java.io.Serializable;

import static pers.jyx.blog.Constant.DOMAIN_NAME;

/**
 * 用户的详细信息
 * @author jyx
 */
@Getter
@Setter
@Entity
@Table(name = DOMAIN_NAME + "_user_detail")
public class UserDetailDO implements Serializable {


    @GenericGenerator(name = "generator", strategy = "foreign",
            parameters = @Parameter(name = "property", value = "user"))
    @Id
    @GeneratedValue(generator = "generator")
    @Column(name = "user_id", unique = true, nullable = false)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @PrimaryKeyJoinColumn
    @JsonBackReference
    private UserDO user;

//    /**
//     *  用户
//     * 每日创建任务个数
//     */
//    private Integer taskCreatedNumPerDay;


}
