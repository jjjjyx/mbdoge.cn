package pers.jyx.blog.article.model;

import cn.mbdoge.jyx.jwt.core.DataView;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
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
@Table(name = DOMAIN_NAME + "_article", uniqueConstraints = {
        @UniqueConstraint(columnNames = "name")
})
public class ArticleDO implements Serializable {
    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = new Date();
    }

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    private String title;
    private String content;
    private String tags;

    // 文章的当前状态
    private Status status;

    // 文章的评论状态
    private CommentStatus commentStatus;

    // 文章的发布时间
    private Date postAt;
    // 文章的阅读数量， 点赞


    @JsonView(DataView.AdminView.class)
    private Date createdAt;

    @JsonView(DataView.AdminView.class)
    private Date updatedAt;
    /**
     * 在后台的排序
     */
    @JsonView(DataView.AdminView.class)
    private int seqInManage;

    /**
     * 文章的版本
     */
    @JsonView(DataView.AdminView.class)
    private int ver;


    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonView(DataView.AdminView.class)
    private UserDO user;

    public enum Status {
        DRAFT,
        PUBLISH,
        PRIVATE,
        PENDING,
        INHERIT
    }

    public enum CommentStatus {
        open,
        close,
        readOnly
    }

}
