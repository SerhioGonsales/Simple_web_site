package com.lukushin.simple_web_site.controller;

import com.lukushin.simple_web_site.entity.User;
import com.lukushin.simple_web_site.enums.Role;
import com.lukushin.simple_web_site.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public String userList(Model model){
        model.addAttribute("users", userService.findAll());
        return "userList";
    }

    @GetMapping("{user}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String userEditForm(@PathVariable User user, Model model){
        model.addAttribute("user", user);
        model.addAttribute("roles", Role.values());
        return "userEdit";
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public String saveUser(
            @RequestParam ("userId") User user,
            @RequestParam Map<String, String> form,
            @RequestParam String username
    ){
        userService.saveUser(user, username, form);
        return "redirect:/user";
    }

    @GetMapping("/profile")
    public String profile(
            @AuthenticationPrincipal User user,
            Model model
    ){
        model.addAttribute("user", user);
        return "profile";
    }

    @PostMapping("/profile")
    public String updateProfile(
            @AuthenticationPrincipal User user,
            @RequestParam String mail,
            @RequestParam String password
    ){
        userService.updateProfile(user, mail, password);
        return "redirect:/user/profile";
    }

    @GetMapping("/subscribe/{user}")
    public String subscribe(
            @AuthenticationPrincipal User currentUser,
            @PathVariable User user
    ){
        userService.subscribe(user, currentUser);
        return "redirect:/user/messages/" + user.getId();
    }

    @GetMapping("/unsubscribe/{user}")
    public String unsubscribe(
            @AuthenticationPrincipal User currentUser,
            @PathVariable User user
    ){
        userService.unsubscribe(user, currentUser);
        return "redirect:/user/messages/" + user.getId();
    }

    @GetMapping("{type}/{user}/list")
    public String userList(
            @PathVariable String type,
            @PathVariable User user,
            Model model
    ){
        if(type.equals("subscriptions")){
            model.addAttribute("users", user.getSubscriptions());
            model.addAttribute("type", "Подписки");
        } else {
            model.addAttribute("users", user.getSubscribers());
            model.addAttribute("type", "Подписчики");

        }
        model.addAttribute("userChannel", user);
        return "subscriptions";
    }

}
