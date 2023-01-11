package com.kata312.controller;

import com.kata312.model.User;
import com.kata312.service.RoleServiceImpl;
import com.kata312.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.List;

@Controller

public class UserController {
    private final UserServiceImpl userService;

    @Autowired
    public UserController(UserServiceImpl userService) {
        this.userService = userService;

    }

    @GetMapping("/user/user")
    public String showPrincipal(Model model, Principal principal) {
       String userMail = principal.getName();
       User user= userService.getUserByEmail(userMail);
       String rolesString= userService.getRolesToString(user);
       model.addAttribute("rolesString", rolesString);
       model.addAttribute("userPrincipal",user);

       return "/user/user";
    }

}
