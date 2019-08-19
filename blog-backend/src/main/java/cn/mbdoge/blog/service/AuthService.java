package cn.mbdoge.blog.service;



import cn.mbdoge.blog.exception.LocalServiceException;
import cn.mbdoge.blog.model.dao.ConfigRepository;
import cn.mbdoge.blog.model.dao.UserRepository;
import cn.mbdoge.blog.model.entities.UserEntity;
import cn.mbdoge.blog.model.pojo.UserDto;
import cn.mbdoge.blog.secruity.JwtTokenProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Optional;

/**
 * @author jyx
 */
@Service
@Slf4j
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;
    private final ConfigRepository configRepository;
    private final PasswordEncoder passwordEncoder;

//    @Value("${user.version}")
//    private int currUserVersion;

    @Autowired
    public AuthService(
            AuthenticationManager authenticationManager,
            JwtTokenProvider jwtTokenProvider,
            UserRepository userRepository, ConfigRepository configRepository, PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;

        this.jwtTokenProvider = jwtTokenProvider;
        this.userRepository = userRepository;
        this.configRepository = configRepository;


        this.passwordEncoder = passwordEncoder;
    }

    public String login(String username, String password) {
        // BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        log.debug("login username = {} password = {}", username, password);

        UsernamePasswordAuthenticationToken upToken = new UsernamePasswordAuthenticationToken(username, password);

        UserDetails userDetails;

        try {
            final Authentication authentication = authenticationManager.authenticate(upToken);
            userDetails = (UserDetails) authentication.getPrincipal();

        } catch (AuthenticationException e) {
            // 用户已过期应当提示
            if (e instanceof AccountExpiredException) {
                throw e;
            } else {
                // 其他情况都提示错误
                log.trace("用户登录失败", e);
                throw new LocalServiceException("auth.login.fail", e);
            }
        }

        log.debug("login userDetails = {}", userDetails);

        return jwtTokenProvider.createToken(userDetails);
    }

    public UserEntity register(UserDto userDto) {
        log.debug("注册用户 User = {}", userDto);
        final String username = userDto.getUsername();

        Optional<UserEntity> user = userRepository.findByUsername(username);


        if (user.isPresent()) {
            log.debug("用户已存在，注册失败");
            throw new LocalServiceException("auth.register.fail");
        }

        final String rawPassword = userDto.getPassword();
        UserEntity newUser = new UserEntity();
        newUser.setUsername(username);
        newUser.setPassword(passwordEncoder.encode(rawPassword));

        if (StringUtils.isEmpty(userDto.getNickname())) {
            newUser.setNickname(username);
        } else {
            newUser.setNickname(userDto.getNickname());
        }

        if (StringUtils.isEmpty(userDto.getDisplayName())) {
            newUser.setDisplayName(username);
        } else {
            newUser.setNickname(userDto.getDisplayName());
        }
        // 默认30天
        long expire =  30L * 24 * 60 * 60 * 1000;
        if (userDto.getExpire() >= 0) {
            expire = userDto.getExpire();
        }


        newUser.setNextExpireTime(new Date(System.currentTimeMillis() + expire));
        newUser.setCreatedAt(new Date());
        newUser.setUpdatedAt(new Date());

//        newUser.setVer(currUserVersion);

        userRepository.save(newUser);
        log.debug("注册成功，试用期限为 {}ms" , expire);
        return newUser;
    }

}
