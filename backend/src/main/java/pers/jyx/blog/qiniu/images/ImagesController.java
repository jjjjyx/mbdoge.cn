package pers.jyx.blog.qiniu.images;

import com.qiniu.storage.model.FileListing;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pers.jyx.blog.Constant;
import pers.jyx.blog.user.model.UserRole;

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
