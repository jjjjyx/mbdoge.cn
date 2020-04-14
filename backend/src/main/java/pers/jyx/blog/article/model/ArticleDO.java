package pers.jyx.blog.article.model;

import lombok.Getter;
import lombok.Setter;

import cn.mbdoge.jyx.jwt.core.DataView;
import com.fasterxml.jackson.annotation.JsonView;
import org.hibernate.annotations.ResultCheckStyle;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLDeleteAll;
import pers.jyx.blog.user.model.UserDO;
import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

import static javax.persistence.GenerationType.IDENTITY;
import static pers.jyx.blog.Constant.DOMAIN_NAME;

@Getter
@Setter
@Entity
@Table(name = DOMAIN_NAME + "_article")
@SQLDelete(sql = "update mbdoge_article s set s.deleted_at = now(), s.status = 'DELETE' where id = ?", check = ResultCheckStyle.COUNT)
@SQLDeleteAll(sql = "update mbdoge_article s set s.deleted_at = now(), s.status = 'DELETE' where id in (?)", check = ResultCheckStyle.COUNT)
public class ArticleDO implements Serializable {
    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = new Date();
    }

    @PreRemove
    protected void onRemove () {
        this.deletedAt = new Date();
        this.status = Status.DELETE;
    }


    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    private String title;
    private String content;
    private String tags;

    /* 短链 */
    private String shortChain;

    /* 文章类别 */
    @Enumerated(EnumType.STRING)
    private Type type;

    /* 文章的当前状态 */
    @Enumerated(EnumType.STRING)
    private Status status;

    /* 文章的评论状态 */
    @Enumerated(EnumType.STRING)
    private CommentStatus commentStatus;

    /*文章的发布时间 */
    private Date postAt;
    // 文章的阅读数量， 点赞
    // 评论数量 数量这个值如果不是在这里的话 每次都得去查询，也怪费事的 直接增加一个字段进行维护
    private int eye;
    private int likes;
    private int comment;
    // 置顶
    private boolean fixedTop;

    private Attribute attribute;

    @JsonView(DataView.AdminView.class)
    private Date createdAt;

    @JsonView(DataView.AdminView.class)
    private Date updatedAt;

    /**
     * 删除时间
     */
    @JsonView(DataView.AdminView.class)
    private Date deletedAt;

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
    private UserDO user;

    @ManyToOne
    @JoinColumn
    private CategoryDO category;

    public enum Type {
        ORIGINAL("原创"),
        REPRODUCE("转载");
        @Getter
        private final String label;

        Type(String label) {
            this.label = label;
        }
    }

    public enum Status {
        DRAFT("草稿"),
        PUBLISH("发布"),
        PRIVATE("私有"),
        DELETE("已删除"),
        PENDING("待处理");
        @Getter
        private final String label;

        Status(String label) {
            this.label = label;
        }
    }

    public enum CommentStatus {
        OPEN("打开"),
        CLOSE("关闭"),
        READ_ONLY("只读");

        @Getter
        private final String label;

        CommentStatus(String label) {
            this.label = label;
        }
    }

    /**
       文章的配置属性

       封面图
       封面类型 上左右
           上：支持封面图轮播
       置顶
       文章密码
    */
    @Getter
    @Setter
    public static class Attribute implements Serializable {
        // 当且 位置为上的生活多个有轮播效果，否则只使用第一个
        private List<String> covers;
        // 0 上 1 左 2 右
        private int coverPosition;

    }
}
