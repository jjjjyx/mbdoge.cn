package pers.jyx.blog;


import cn.mbdoge.jyx.encrypt.PasswordHashAsmCrypt;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;


@SpringBootApplication
@EnableConfigurationProperties(MiscProperties.class)
@Slf4j
public class Application extends SpringBootServletInitializer implements CommandLineRunner {
//    @Autowired
//    PasswordEncoder passwordEncoder;
//    @Autowired
//    UserRepository userRepository;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        log.info("Application is running!");
//        String salt = "1U$&6gpq$mFnCdKcrJjYmW@8%2Xy0WGT";

//        String s = PasswordHashAsmCrypt.passwordHash("123456", salt.toCharArray(), 40000);
//        System.out.println("s = " + s);
//        System.out.println("passwordEncoder.encode(s) = " + passwordEncoder.encode(s));

    }
}
