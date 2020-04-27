package pers.jyx.blog.qiniu.images;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class ImageInfoVO implements Serializable {
    public String src;
    // public String msrc;
    // public String title;
    public int w;
    public int h;
}
