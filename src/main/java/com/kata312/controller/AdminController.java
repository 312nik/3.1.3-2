package com.kata312.controller;

import com.kata312.model.Role;
import com.kata312.model.User;

import com.kata312.service.RoleService;
import com.kata312.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Controller

public class AdminController {

    private final UserService userService;
    private final RoleService roleService;

    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }


    @GetMapping("/admin/users")
    public String showAllUsers(Model model, Principal principal) {
        String userMail = principal.getName();
        User user= userService.getUserByEmail(userMail);
        String rolesString= userService.getRolesToString(user);
        model.addAttribute("rolesString", rolesString);
        model.addAttribute("userPrincipal",user);

        List<User> users = userService.getAllUsers();
        model.addAttribute("users", users);
        return "/admin/users";
    }


    @GetMapping("admin/new")
    public String createUserForm(User user,Model model) {
        List <Role> roles= roleService.getAllRole();
        model.addAttribute("roles",roles);
        return "admin/new";
    }

    @PostMapping("admin/new")
    public String createUser(@ModelAttribute("user") @Valid User user, @RequestParam(value = "selectRoles") String[] selectRole,
                             BindingResult bindingResult, Model model) {

      try {
       if ( userService.getUserByEmail(user.getEmail()) != null) {
        model.addAttribute("emailError", "Пользователь с таким email уже существует");
            List <Role> roles= roleService.getAllRole();
            model.addAttribute("roles",roles);


        return "/admin/new";
       }
      }catch (Exception ignore) {}




        List <Role> userRole =  new ArrayList<>();
        for (String role: selectRole ) {
            userRole.add(roleService.getRoleByName(role));
        }
        user.setRoles(userRole);



    userService.addUser(user);
        return "redirect:/admin/users";

    }

    @PostMapping("/admin/delete/{id}")
    public String deleteUser(@PathVariable("id") Long id, Model model) {

        userService.removeUserById(id);
        return "redirect:/admin/users";

    }

    @GetMapping("/admin/edit/{id}")
    public String edit(@PathVariable("id") Long id, Model model) {
        List<Role> roles=  roleService.getAllRole();
        model.addAttribute("roles",roles);
        User user = userService.getUserById(id);
        model.addAttribute("user", user);
        return "/admin/edit";

    }

    @PostMapping("/admin/edit")
    public  String update( User user, @RequestParam(value = "selectRoles") String[] selectRole,
                           BindingResult bindingResult, Model model) {
        List <Role> roles =  new ArrayList<>();
        for (String role: selectRole ) {
            roles.add(roleService.getRoleByName(role));
        }
        user.setRoles(roles);
        userService.updateUser(user);
        return "redirect:/admin/users";

    }

}




