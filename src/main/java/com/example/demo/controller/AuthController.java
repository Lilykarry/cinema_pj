package com.example.demo.controller;


import com.example.demo.model.Admins;
import com.example.demo.model.Users;
import com.example.demo.repository.AdminRepository;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    @Autowired
    private AdminRepository adminRepository;
    @GetMapping("/login")
    public String showLoginPage( Model model, @RequestParam(value = "error", required = false) String error) {
        if (error != null) {
            // Add error message to the model if 'error' parameter exists in the URL
            model.addAttribute("error", "Invalid email or password.");
        }
        return "users/login"; // Trả về tên của trang đăng nhập (login.html)
    }


    @PostMapping("/authentication")
    public String processLogin(@RequestParam("email") String email,
                               @RequestParam("password") String password,
                               Model model, RedirectAttributes redirectAttributes) {

        System.out.println("email: "+email);
        boolean isAuthenticated = userService.authenticate(email, password);

        // Check if authentication is successful.
        if (isAuthenticated) {
            // Redirect to the home page after successful login.
            return "redirect:/guest/home";
        } else {
            // Add error message to the model.
            model.addAttribute("error", "Invalid email or password.");
            return "users/login"; // Return the login page with the error message.
        }
    }


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
