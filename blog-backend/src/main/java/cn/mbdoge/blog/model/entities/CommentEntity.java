package cn.mbdoge.blog.model.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import static javax.persistence.GenerationType.IDENTITY;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "mb_comment")
public class CommentEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private long id;

    @Column(name = "content", length = 1500)
    private String content;
    // 接第三方
    private Date createdAt;

    @Enumerated(EnumType.STRING)
    private Status status;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="post_id")
    private PostEntity post;

    /**父评论*/
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="parent_id")
    @JsonBackReference
    private CommentEntity parent;

    /**子评论*/
    @OneToMany(cascade=CascadeType.ALL,fetch=FetchType.EAGER)
    @JoinColumn(name="parent_id")
    @JsonManagedReference
    private Set<CommentEntity> children = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CommentEntity that = (CommentEntity) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public enum Status{
        /**
         * 删除
         */
        delete,
        /**
         * 通过
         */
        pass,
        /**
         * 等待审核
         */
        review,
        /**
         * 拒绝
         */
        reject
    }

}
