package com.example231.crud.controller;

import com.example231.crud.model.User;
import com.example231.crud.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
public class UsersController {

    @Autowired
    private UserService userService;

    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute("user") User user, Model model) {
        if (!userService.addDefaultUser(user)) {
            model.addAttribute("usernameError", "Пользователь с таким именем уже существует");
            return "register";
        }
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("user", new User());
        return "login";
    }

    @GetMapping("/user")
    public String user(Principal principal, Model model) {
        String name = principal.getName();
        model.addAttribute("message", "Hello " + name);
        return "user";
    }

}



