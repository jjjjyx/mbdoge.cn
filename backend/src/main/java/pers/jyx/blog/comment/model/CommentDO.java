package pers.jyx.blog.comment.model;

import cn.mbdoge.jyx.jwt.core.DataView;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Getter;
import lombok.Setter;
import pers.jyx.blog.user.model.UserDO;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Date;

import static javax.persistence.GenerationType.IDENTITY;
import static pers.jyx.blog.Constant.DOMAIN_NAME;

@Getter
@Setter
@Entity
@Table(name = DOMAIN_NAME + "_comment")
public class CommentDO implements Serializable {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    /* 评论对象 */
    private String target;
    private String content;
    /* 楼层 */
    private int karma;
    private Date createdAt;

    /**父评论*/
    @ManyToOne
    @JoinColumn(name="parent_id")
    private CommentDO parent;


    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserDO user;

    /* 评论者信息 */
//    private String author;
//    private String avatar;
//    private String email;
//    private String url;

    @JsonView(DataView.AdminView.class)
    private String ip;
    private String ua;

    @Enumerated(EnumType.STRING)
    private Status status;

    @JsonView(DataView.AdminView.class)
    public String getOriginContent () {
        return this.content;
    }

    public String getContent() {
        if (status == null) {
            return "";
        }
        switch (status) {
            case DISPLAY:
                return this.content;
            case DELETE:
                return "该评论已被删除";
            case AUDIT:
                return "审核中";
            default:
                return "";
        }
    }

    public enum Status {
        DISPLAY("显示"),
        DELETE("删除"),
        AUDIT("审核");

        @Getter
        private final String label;

        Status(String label) {
            this.label = label;
        }
    }
}
