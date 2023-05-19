package com.example231.crud.controller;

import com.example231.crud.model.Role;
import com.example231.crud.model.User;
import com.example231.crud.repositories.RoleRepository;
import com.example231.crud.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class AdminController {
    private UserService userService;
    private RoleRepository roleRepository;

    public AdminController(UserService userService, RoleRepository roleRepository) {
        this.userService = userService;
        this.roleRepository = roleRepository;
    }

    @GetMapping("/admin")
    public String getAllUser(Model model) {
        model.addAttribute("users", userService.findAllUsers());
        return "admin";
    }

    @GetMapping("/admin/new")
    public String getNew(Model model) {
        model.addAttribute("user", new User());
        List<Role> listRoles = userService.getAllRoles();
        model.addAttribute("listRoles", listRoles);
        return "new";
    }

    @PostMapping("/admin/new")
    public String create(@ModelAttribute("user") User user, Model model) {
        if (!userService.adminAddedUser(user)) {
            model.addAttribute("usernameError", "Пользователь с таким именем уже существует");
            return "new";
        }
        return "redirect:/admin";
    }

    @GetMapping("admin/{id}/edit")
    public String edit(@PathVariable("id") Long id, Model model) {
        model.addAttribute("user", userService.getUserById(id));
        List<Role> listRoles = userService.getAllRoles();
        model.addAttribute("listRoles", listRoles);
        return "edit";
    }

    @PatchMapping("/admin/{id}")
    public String edit(@ModelAttribute("user") User user, @PathVariable("id") Long id) {
        userService.updateUser(id, user);
        return "redirect:/admin";
    }

    @GetMapping("admin/{id}/delete")
    public String delete(@PathVariable(value = "id") Long id, Model model) {
        userService.removeUser(id);
        return "redirect:/admin";
    }

}
