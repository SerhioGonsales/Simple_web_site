package com.lukushin.simple_web_site.controller;

import com.lukushin.simple_web_site.entity.User;
import com.lukushin.simple_web_site.repository.UserRepository;
import com.lukushin.simple_web_site.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

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
    public String addUser(
            @RequestParam("password2") String passwordConfirmation,
            @Valid User user,
            BindingResult bindingResult,
            Model model
    ){
        boolean confirmEmpty = StringUtils.isEmpty(passwordConfirmation);

        if(confirmEmpty){
            model.addAttribute("password2Error", "Введите пароль еще раз");
        }

        if(bindingResult.hasErrors()){
            Map<String, String> errorMap = ControllerUtils.getErrorsMap(bindingResult);
            model.mergeAttributes(errorMap);
            model.addAttribute("user", user);
            return "/registration";
        }

        if((user.getPassword() != null
                && user.getPassword().equals(passwordConfirmation))){

            // TODO проверить view на отображение userError
            if(!userService.addUser(user)){
                model.addAttribute("userError",
                        "Такой пользователь уже существует!");
                return "/registration";
            }
            model.addAttribute("message",
                    "Вам на почту выслано письмо для подтверждения регистрации.");
            return "/login";
        }
        model.addAttribute("password2Error", "Пароли не совпадают");
        return "/registration";
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
        return "/login";
    }
}
