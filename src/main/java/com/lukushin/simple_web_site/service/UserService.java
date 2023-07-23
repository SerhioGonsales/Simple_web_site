package com.lukushin.simple_web_site.service;

import com.lukushin.simple_web_site.entity.User;
import com.lukushin.simple_web_site.enums.Role;
import com.lukushin.simple_web_site.repository.UserRepository;
import org.aspectj.bridge.IMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Collections;
import java.util.UUID;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MailService mailService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUserName(username);
    }

    public boolean saveUser(User user){
        User userFromDb = userRepository.findByUserName(user.getUserName());
        if(userFromDb != null){
            return false;
        }
        user.setActive(true);
        user.setRole(Collections.singleton(Role.USER));
        user.setActivationCode(UUID.randomUUID().toString());
        userRepository.save(user);

        if(!StringUtils.isEmpty(user.getMail())){
            String text = String.format("Привет, %s!\n" +
                            "Для активации своего аккаунта пройдите по ссылке \n" +
                            "http://localhost:8080/activation/" + user.getActivationCode(),
                    user.getUserName());
            mailService.sendMail(user.getMail(),"Активация пользователя на Simple web site", text);
        }
        return true;
    }

    public boolean activateUser(String code) {
        User user = userRepository.findByActivationCode(code);
        if(user == null){
            return false;
        }
        return true;
    }
}