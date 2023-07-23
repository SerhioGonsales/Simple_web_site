package com.lukushin.simple_web_site.controller;

import com.lukushin.simple_web_site.entity.User;
import com.lukushin.simple_web_site.repository.UserRepository;
import com.lukushin.simple_web_site.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("")
public class RegistrationController {
    @Autowired
    UserService userService;
    @Autowired
    UserRepository userRepository;

    @GetMapping("/registration")
    public String registration(){
        return "registration";
    }

    @PostMapping("/registration")
    public String addUser(User user, Model model){

        if(!userService.saveUser(user)){
            model.addAttribute("message", "Такой пользователь уже существует!");
            return "registration";
        }
        model.addAttribute("message",
                "Вам на почту выслано письмо для подтверждения регистрации.");

        return "login";
    }

    @GetMapping("/activation/{code}")
    public String activate(@PathVariable String code, Model model){
        boolean isActivated = userService.activateUser(code);
        if(!isActivated){
            model.addAttribute("message",
                    "Извините, такой учетной записи не найдено!");
        }
        model.addAttribute("message",
                "Ваша учетная запись успешно активирована!");
        return "login";
    }
}
