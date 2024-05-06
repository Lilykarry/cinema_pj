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
    public String showLoginPage(@RequestParam(value = "error", required = false) String error, Model model) {
        // Nếu có lỗi, thêm thông báo lỗi vào model
        if (error != null) {
            model.addAttribute("loginError", "Invalid email or password. Please try again.");
        }
        return "users/login"; // Trả về tên của trang đăng nhập (login.html)
    }
    @PostMapping("/login")
    public String processLogin(@RequestParam("email") String email,
                               @RequestParam("password") String password,
                               Model model, RedirectAttributes redirectAttributes) {
        // Tìm kiếm người dùng dựa trên tên người dùng được cung cấp.
        boolean isAuthenticated = userService.authenticate(email, password);
//        Admins admins=adminRepository.findAdminsByEmail(email);

        // Kiểm tra xem người dùng có tồn tại và mật khẩu có khớp không.
        if (isAuthenticated) {
            // Đăng nhập thành công.
            // Bạn có thể sử dụng các phương thức khác để thiết lập phiên đăng nhập, v.v.
            return "redirect:/guest/home"; // Chuyển hướng đến trang chính.
        }
//        else if (admins != null && passwordEncoder.matches(password, admins.getPassword())) {
//            // Đăng nhập thành công.
//            // Bạn có thể sử dụng các phương thức khác để thiết lập phiên đăng nhập, v.v.
//            return "redirect:/Admin/Home"; // Chuyển hướng đến trang chính.
//        }
        else {
            // Đăng nhập thất bại.
            model.addAttribute("mess", "Tên người dùng hoặc mật khẩu không chính xác.");
            return "users/login"; // Hiển thị lại trang đăng nhập với thông báo lỗi.
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
