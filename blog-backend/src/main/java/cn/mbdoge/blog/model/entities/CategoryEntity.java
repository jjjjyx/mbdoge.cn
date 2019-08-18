package cn.mbdoge.blog.model.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import static javax.persistence.GenerationType.IDENTITY;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "mb_category")
public class CategoryEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private int id;
    private String name;
    private String shortId;
    private String icon;
    private String description;

}
