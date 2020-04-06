package pers.jyx.blog.user.server;



import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import pers.jyx.blog.user.model.OnlineUserVO;
import pers.jyx.blog.user.model.UserDO;
import pers.jyx.blog.user.model.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author jyx
 */

@Slf4j
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;


    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDO user = userRepository
                .findByUsername(username)
                .orElseThrow(() ->  new UsernameNotFoundException(String.format("No user found with username '%s'.", username)));
        log.debug("load user by username = {}, obj = {}", username, user);

        OnlineUserVO vo = new OnlineUserVO();
        vo.setId(user.getId());
        vo.setUsername(user.getUsername());
        vo.setNextExpireTime(user.getNextExpireTime());
        vo.setPassword(user.getPassword());
        vo.setUserStatus(user.getUserStatus());
        // 不在在redis 序列化的时候会不会出现循环调用
        vo.setDetail(user.getUserDetail());
        List<GrantedAuthority> roles = user.getRoles()
                .stream()
                .map(item -> new SimpleGrantedAuthority(item.getName().toUpperCase()))
                .collect(Collectors.toList());
        vo.setAuthorities(roles);
        return vo;
    }
}
