package com.lukushin.simple_web_site.service;

import com.lukushin.simple_web_site.entity.User;
import com.lukushin.simple_web_site.enums.Role;
import com.lukushin.simple_web_site.repository.UserRepository;
import org.aspectj.bridge.IMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MailService mailService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUserName(username);
    }

    public boolean addUser(User user){
        User userFromDb = userRepository.findByUserName(user.getUserName());
        if(userFromDb != null){
            return false;
        }
        user.setActive(true);
        user.setRole(Collections.singleton(Role.USER));
        user.setActivationCode(UUID.randomUUID().toString());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);

        sendActivateMessage(user);
        return true;
    }

    public void sendActivateMessage(User user) {
        if(!StringUtils.isEmpty(user.getMail())){
            String text = String.format("Привет, %s!\n" +
                            "Для активации своего аккаунта пройдите по ссылке \n" +
                            "http://localhost:8080/activation/%s",
                    user.getUserName(),
                    user.getActivationCode());
            mailService.sendMail(user.getMail(),"Активация пользователя на Simple web site", text);
        }
    }

    public boolean activateUser(String code) {
        User user = userRepository.findByActivationCode(code);
        if(user == null){
            return false;
        }
        user.setActivationCode(null);
        userRepository.save(user);
        return true;
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public void saveUser(User user, String username, Map<String, String> form) {
        user.setUserName(username);
        Set<String> roles = Arrays.stream(Role.values())
                .map(Role::name)
                .collect(Collectors.toSet());
        user.getRole().clear();
        for (String key : form.keySet()){
            if(roles.contains(key)){
                user.getRole().add(Role.valueOf(key));
            }
        }
        userRepository.save(user);
    }

    public void updateProfile(User user, String mail, String password) {
        String userMail = user.getMail();
        boolean isEmailChanged = (mail!=null && !mail.equals(userMail)) ||
                (userMail!=null && !userMail.equals(mail));

        if(isEmailChanged){
            user.setMail(mail);
            if(!StringUtils.isEmpty(mail)){
                user.setActivationCode(UUID.randomUUID().toString());
                sendActivateMessage(user);
            }
        }

        if(!StringUtils.isEmpty(password)){
            user.setPassword(passwordEncoder.encode(password));
        }
        userRepository.save(user);
    }

    public void subscribe(User user, User currentUser) {
        user.getSubscribers().add(currentUser);
        userRepository.save(user);
    }

    public void unsubscribe(User user, User currentUser) {
        user.getSubscribers().remove(currentUser);
        userRepository.save(user);
    }
}