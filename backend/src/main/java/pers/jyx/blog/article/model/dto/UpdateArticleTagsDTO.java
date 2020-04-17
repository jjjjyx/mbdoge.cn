package pers.jyx.blog.article.model.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Collections;
import java.util.List;

@Getter
@Setter
public class UpdateArticleTagsDTO {
    private List<String> tags = Collections.emptyList();
}
