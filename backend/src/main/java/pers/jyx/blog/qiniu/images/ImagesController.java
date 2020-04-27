package pers.jyx.blog.qiniu.images;

import com.qiniu.storage.model.FileListing;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pers.jyx.blog.Constant;
import pers.jyx.blog.user.model.UserRole;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = Constant.API_SERVLET_URL_PREFIX + "/images")
@PreAuthorize("hasRole('" + UserRole.ADMIN + "')")
public class ImagesController {

    @Autowired
    private QiniuService qiniuService;

    @GetMapping(value = "token")
    public String token(@RequestParam(value = "space", required = false, defaultValue = "default") String space) {
        return qiniuService.getTokenBySpace(space);
    }

    @PostMapping("info")
    @PreAuthorize("hasRole('" + UserRole.ADMIN + "')")
    public List<ImageInfoVO> queryImageInfo(@RequestBody ImagesInfoQueryCriteriaDTO dto) {
        return qiniuService.queryImagesInfo(dto);
    }

    @GetMapping
    @PreAuthorize("hasRole('" + UserRole.ADMIN + "')")
    public FileListing getImageAll(Pageable pageable, @Validated ImagesQueryCriteriaDTO criteria) {
        return qiniuService.queryImages(pageable, criteria);
    }

//    @GetMapping(value = "token")
//    public String token () {
//        return "";
//    }
}
