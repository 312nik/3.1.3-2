package com.kata312.controller;

import com.kata312.model.Role;
import com.kata312.model.User;
import com.kata312.service.RoleServiceImpl;
import com.kata312.service.UserServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller

public class AdminController {

    private final UserServiceImpl userService;
    private final RoleServiceImpl roleService;

    @Autowired
    public AdminController(UserServiceImpl userService, RoleServiceImpl roleService) {

        this.userService = userService;
        this.roleService = roleService;

    }



    @GetMapping("/admin/users")
    public String showAllUsers(Model model, Principal principal) {
        String userMail = principal.getName();
        User user= userService.findUserByEmail(userMail);
        String rolesString= userService.getRolesToString(user);
        model.addAttribute("rolesString", rolesString);
        model.addAttribute("userPrincipal",user);

        List<User> users = userService.findAll();
        model.addAttribute("users", users);
        return "/admin/users";
    }


    @GetMapping("admin/new")
    public String createUserForm(User user,Model model) {
        user = new User();
        List <Role> roles= roleService.getAllRole();
        model.addAttribute("roles",roles);
        model.addAttribute("user",user);
        return "admin/new";
    }

    @PostMapping("admin/new")
    public String createUser(@ModelAttribute("user") @Valid User user, @RequestParam(value = "selectRoles") String[] selectRole,
                             BindingResult bindingResult, Model model) {

        if ( userService.findUserByEmail(user.getEmail()) != null) {
        model.addAttribute("emailError", "Пользователь с таким email уже существует");
            List <Role> roles= roleService.getAllRole();
            model.addAttribute("roles",roles);


        return "/admin/new";
    }
        Set <Role> roles =  new HashSet<>();
        for (String role: selectRole ) {
           roles.add(roleService.getRoleByName(role));
        }
        user.setRoles(roles);
    userService.save(user);
        return "redirect:/admin/users";

    }

    @PostMapping("/admin/delete/{id}")
    public String deleteUser(@PathVariable("id") Long id, Model model) {
        User user = userService.findById(id);
        userService.deleteUser(user);
        return "redirect:/admin/users";

    }

    @GetMapping("/admin/edit/{id}")
    public String edit(@PathVariable("id") Long id, Model model) {
        List<Role> roles=  roleService.getAllRole();
        model.addAttribute("roles",roles);
        User user = userService.findById(id);
        model.addAttribute("user", user);
        return "/admin/edit";

    }

    @PostMapping("/admin/edit")
    public  String update( User user, @RequestParam(value = "selectRoles") String[] selectRole,
                           BindingResult bindingResult, Model model) {







        Set <Role> roles =  new HashSet<>();
        for (String role: selectRole ) {
            roles.add(roleService.getRoleByName(role));
        }
        user.setRoles(roles);
        userService.save(user);
        return "redirect:/admin/users";

    }

}




