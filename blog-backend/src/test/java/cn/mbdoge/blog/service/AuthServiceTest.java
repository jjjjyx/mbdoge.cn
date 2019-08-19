package cn.mbdoge.blog.service;


import cn.mbdoge.blog.exception.LocalServiceException;

import cn.mbdoge.blog.model.entities.UserEntity;
import cn.mbdoge.blog.model.pojo.UserDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

import javax.transaction.Transactional;
import java.util.Date;


@SpringBootTest
@ActiveProfiles("private")
//@Transactional
public class AuthServiceTest {
    @Autowired
    private AuthService authService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    public void login() {

        String username = "test-create22";
        String password = "12345678";
        String token = authService.login(username, password);

        Assertions.assertNotNull(token);
        System.out.println("token = " + token);


        // 错误的密码
        LocalServiceException localServiceException = Assertions.assertThrows(LocalServiceException.class, ()-> {
            authService.login(username, "1231321321");
        });

        Assertions.assertEquals("auth.login.fail", localServiceException.getMessage());


        // 错误的账号
        localServiceException = Assertions.assertThrows(LocalServiceException.class, ()-> {
            authService.login("asdas", "1231321321");
        });

        Assertions.assertEquals("auth.login.fail", localServiceException.getMessage());
    }

    @Test
    public void register() {
        UserDto userDto = new UserDto();
        userDto.setUsername("jjjjyx");
        // 12345678 => djAxVkc4Z2FHRjJaU0JoSUhCaGMzTjNiM0prTENCb1lYWmxJR0VnYzJGc2RBPT0BhqDHW0an85kDOtehxzXVNhFZGyDrC0ZQTMufh8kTrQDwGg
        userDto.setPassword("djAxVkc4Z2FHRjJaU0JoSUhCaGMzTjNiM0prTENCb1lYWmxJR0VnYzJGc2RBPT0BhqDHW0an85kDOtehxzXVNhFZGyDrC0ZQTMufh8kTrQDwGg==");
        userDto.setEmail("jyx@rpgame.net");
        userDto.setExpire(99999999999L);
        userDto.setDisplayName("超级管理员");

        UserEntity userEntity = authService.register(userDto);

        Assertions.assertEquals("jjjjyx" , userEntity.getUsername());
        Assertions.assertNotEquals("12345678", userEntity.getPassword());
         System.out.println("userEntity = " + userEntity);
        Assertions.assertTrue(passwordEncoder.matches("djAxVkc4Z2FHRjJaU0JoSUhCaGMzTjNiM0prTENCb1lYWmxJR0VnYzJGc2RBPT0BhqDHW0an85kDOtehxzXVNhFZGyDrC0ZQTMufh8kTrQDwGg==", userEntity.getPassword()));


        // userEntity = new UserEntity();
        // userEntity.setUsername("test-create22");
        // userEntity.setPassword("password22");
        //
        //
        // final UserEntity finalUserEntity = userEntity;
        // LocalServiceException localServiceException = Assertions.assertThrows(LocalServiceException.class, ()-> {
        //     authService.register(finalUserEntity);
        // });
        // Assertions.assertEquals("auth.register.fail", localServiceException.getMessage());
    }

}
