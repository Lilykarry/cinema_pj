package com.example.demo.controller;


import com.example.demo.model.Admins;
import com.example.demo.model.Users;
import com.example.demo.repository.AdminRepository;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    @Autowired
    private AdminRepository adminRepository;

    @GetMapping("login")
    public String index() {
        return "users/login";
    }

    @RequestMapping(value = "/login", params = "error=true")
    public String loginError(Model model) {
        System.out.println("ok");
        model.addAttribute("error", "Invalid username or password !");
        return "users/login";
    }


//    @PostMapping("/authentication")
//    public String processLogin(@RequestParam("email") String email,
//                               @RequestParam("password") String password,
//                               Model model, RedirectAttributes redirectAttributes) {
//
//        System.out.println("email: "+email);
//        boolean isAuthenticated = userService.authenticate(email, password);
//
//        // Check if authentication is successful.
//        if (isAuthenticated) {
//            // Redirect to the home page after successful login.
//            return "redirect:/guest/home";
//        } else {
//            // Add error message to the model.
//            model.addAttribute("error", "Invalid email or password.");
//            return "users/login"; // Return the login page with the error message.
//        }
//    }


    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new Users());
        return "users/register";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute("user") Users user) {
        userService.register(user);
        return "redirect:/login"; // Redirect to login page after successful registration
    }
}
