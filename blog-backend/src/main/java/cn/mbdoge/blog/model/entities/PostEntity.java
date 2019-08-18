package cn.mbdoge.blog.model.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static javax.persistence.GenerationType.IDENTITY;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "mb_post")
public class PostEntity implements Serializable {


    private static final long serialVersionUID = 2805568915514064730L;

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private long id;

    @Lob
    @Column(name = "content", length = 512)
    private String content;
    private String title;
    /**
     * 摘录
     */
    private String excerpt;

    @Enumerated(EnumType.STRING)
    private Status status;

    @Enumerated(EnumType.STRING)
    private CommentStatus commentStatus;

    private String password;

    // 这个值决定 url
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author")
    private UserEntity author;

    @ManyToOne
    @JoinColumn(name = "category")
    private CategoryEntity category;

    @ManyToMany(cascade = {CascadeType.REFRESH}, fetch = FetchType.LAZY)
    private List<TagEntity> tags = new ArrayList<>();

    private Date publishDate;
    private Date createdAt;
    private Date updatedAt;

    public enum Status {
        draft,
        publish,
        inherit,
        pending
    }

    public enum CommentStatus {
        open,
        close,
    }

    @Override
    public String toString() {
        return "PostEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", title='" + title + '\'' +
                ", status=" + status +
                '}';
    }
}
