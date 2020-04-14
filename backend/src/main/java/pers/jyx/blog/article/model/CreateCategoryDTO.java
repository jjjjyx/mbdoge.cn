package pers.jyx.blog.article.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateCategoryDTO {
    private String name;
    private String desc;
    private String icon;
}
