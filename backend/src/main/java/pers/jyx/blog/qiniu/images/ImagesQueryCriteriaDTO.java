package pers.jyx.blog.qiniu.images;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class ImagesQueryCriteriaDTO {
    @NotNull
    private String space;

    private String marker;
    private int limit;
    private String date;
}
