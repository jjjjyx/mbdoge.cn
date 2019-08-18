package cn.mbdoge.blog.model.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import java.util.Objects;

import static javax.persistence.GenerationType.IDENTITY;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "mb_tags")
public class TagEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private int id;
    private String name;
    private String shortId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TagEntity tagEntity = (TagEntity) o;
        return id == tagEntity.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
