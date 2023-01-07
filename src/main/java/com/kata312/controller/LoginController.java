package com.kata312.controller;

import com.kata312.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;

@Controller
public class LoginController {



    @GetMapping("/login")
    public String getLogin(@RequestParam (value="error", required = false) String error,
                           @RequestParam (value="logout", required = false)String logout,
                           Model model) {
        model.addAttribute("eroor",error!=null);
        model.addAttribute("logout",logout!=null);
        return "login";
    }
}
