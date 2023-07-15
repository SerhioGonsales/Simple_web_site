package com.lukushin.simple_web_site.configuration;

import com.lukushin.simple_web_site.entity.MyUser;
import com.lukushin.simple_web_site.repository.UserRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        MyUser myUser = userRepository.findByUserName(username);
        if(myUser == null){
            throw new UsernameNotFoundException("Unknown user: " + username);
        }
        UserDetails user = User.builder()
                .username(myUser.getUserName())
                .password(myUser.getPassword())
                .roles(myUser.getRole().toString())
                .build();
        return user;
    }
}