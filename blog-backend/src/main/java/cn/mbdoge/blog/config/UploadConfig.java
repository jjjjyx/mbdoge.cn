package cn.mbdoge.blog.config;

import org.springframework.context.annotation.Configuration;

@Configuration
public class UploadConfig {


//    @Bean
//    public MultipartConfigElement multipartConfigElement() {
//        MultipartConfigFactory factory = new MultipartConfigFactory();
//        // 设置文件大小限制 ,超出设置页面会抛出异常信息，
//        // 这样在文件上传的地方就需要进行异常信息的处理了;
//        factory.setMaxFileSize("256KB"); // KB,MB
//        factory.setMaxFileSize(DataSize.of());
//        /// 设置总上传数据总大小
//        factory.setMaxRequestSize("512KB");
//        // Sets the directory location where files will be stored.
//        // factory.setLocation("路径地址");
//        return factory.createMultipartConfig();
//    }

//    @Value("${upload.store.path}")
//    private String UPLOAD_STORE_PATH;

//    public UploadConfig() {
//        // todo 在 liunx  上会不会没有权限
//        System.out.println("UPLOAD_STORE_PATH = " + UPLOAD_STORE_PATH);
//    }
}
