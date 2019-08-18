package cn.mbdoge.blog.model.entities;

import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

import static javax.persistence.GenerationType.IDENTITY;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "mb_post_meta")
public class PostMetaEntity implements Serializable {

    private static final long serialVersionUID = 5622150481444656462L;
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private int id;
    private String name;
    @Lob
    @Column(name = "value", length = 512)
    private String value;
    private Date createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private PostEntity post;
}
