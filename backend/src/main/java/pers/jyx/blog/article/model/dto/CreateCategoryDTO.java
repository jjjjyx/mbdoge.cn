package pers.jyx.blog.article.model.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.Authentication;

@Getter
@Setter
public class CreateCategoryDTO {
    private String name;
    private String description;
    private String icon;
}
