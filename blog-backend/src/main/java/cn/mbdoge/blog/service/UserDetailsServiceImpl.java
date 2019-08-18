package cn.mbdoge.blog.service;


import cn.mbdoge.blog.model.dao.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * @author jyx
 */
@Service
@Slf4j
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;


    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UserDetails user = userRepository.findByUsername(username).orElseThrow(() ->  new UsernameNotFoundException(String.format("No user found with username '%s'.", username)));
        log.debug("load user by username = {}, obj = {}", username, user);
        return user;
    }
}
