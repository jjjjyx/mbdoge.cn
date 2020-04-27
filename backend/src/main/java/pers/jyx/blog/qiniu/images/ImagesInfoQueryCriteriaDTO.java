package pers.jyx.blog.qiniu.images;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.Collections;
import java.util.List;

@Getter
@Setter
public class ImagesInfoQueryCriteriaDTO {
    List<String> keys = Collections.emptyList();
}
