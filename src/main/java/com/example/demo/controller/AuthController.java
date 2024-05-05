package com.example.demo.controller;


import com.example.demo.model.Users;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AuthController {

    @Autowired
    private UserService userService;

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new Users());
        return "users/register";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute("user") Users user) {
        userService.register(user);
        return "redirect:/guest/home"; // Redirect to login page after successful registration
    }
}
