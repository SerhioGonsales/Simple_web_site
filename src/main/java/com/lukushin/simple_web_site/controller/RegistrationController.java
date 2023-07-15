package com.lukushin.simple_web_site.controller;

import com.lukushin.simple_web_site.entity.MyUser;
import com.lukushin.simple_web_site.enums.Role;
import com.lukushin.simple_web_site.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Collections;
import java.util.Map;

@Controller
public class RegistrationController {

    @Autowired
    UserRepository userRepository;

    @GetMapping("/registration")
    public String registration(){
        return "registration";
    }

    @PostMapping("/registration")
    public String addUser(MyUser user, Map<String, Object> model){
        MyUser userFromDb = userRepository.findByUserName(user.getUserName());
        if(userFromDb != null){
            model.put("message", "User exist!");
            return "registration";
        }

        user.setActive(true);
        user.setRole(Collections.singleton(Role.USER));
        userRepository.save(user);
        return "redirect:/login";
    }
}