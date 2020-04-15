package pers.jyx.blog.article.model.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Collections;
import java.util.List;

@Getter
@Setter
public class CreateArticleDTO {

    @NotBlank
    private String title;
    private List<String> tags = Collections.emptyList();
    @NotNull
    private ArticleDO.Type type;

    @NotNull
    private ArticleDO.CommentStatus commentStatus;

    @NotNull
    private String category;

    // 当且 位置为上的生活多个有轮播效果，否则只使用第一个
    private List<String> covers;
    // 0 上 1 左 2 右
    private int coverPosition;
}
