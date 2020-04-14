package pers.jyx.blog.comment.model;

import lombok.Getter;
import lombok.Setter;

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
    private String karma;
    private Date createdAt;

    /**父评论*/
    @ManyToOne
    @JoinColumn(name="parent_id")
    private CommentDO parent;


    /* 评论者信息 */
    private String author;
    private String avatar;
    private String email;
    private String url;
    private String ip;
    private String ua;
    @Enumerated(EnumType.STRING)
    private Status status;

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
