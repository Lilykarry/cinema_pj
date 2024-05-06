//package com.example.demo.controller;
//
//import com.example.demo.model.Admins;
//import com.example.demo.model.Users;
//import com.example.demo.repository.AdminRepository;
//import com.example.demo.service.UserService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//
//@Controller("/")
//public class LoginController {
//    @Autowired
//    private UserService userService;
//    @Autowired
//    private BCryptPasswordEncoder passwordEncoder;
//    @Autowired
//    private AdminRepository adminRepository;
//    @GetMapping("/login")
//    public String index(){
//        return "login/login";
//    }
//    @PostMapping("/login")
//    public String processLogin(@RequestParam("email") String username,
//                               @RequestParam("password") String password,
//                               Model model) {
//        // Tìm kiếm người dùng dựa trên tên người dùng được cung cấp.
//        Users user = userService.findByUsername(username);
//        Admins admins=adminRepository.findAdminsByEmail(username);
//
//        // Kiểm tra xem người dùng có tồn tại và mật khẩu có khớp không.
//        if (user != null && passwordEncoder.matches(password, user.getPassword())) {
//            // Đăng nhập thành công.
//            // Bạn có thể sử dụng các phương thức khác để thiết lập phiên đăng nhập, v.v.
//            return "redirect:/guest/home"; // Chuyển hướng đến trang chính.
//        }
//        else if (admins != null && passwordEncoder.matches(password, admins.getPassword())) {
//            // Đăng nhập thành công.
//            // Bạn có thể sử dụng các phương thức khác để thiết lập phiên đăng nhập, v.v.
//            return "redirect:/Admin/Home"; // Chuyển hướng đến trang chính.
//        }else {
//            // Đăng nhập thất bại.
//            model.addAttribute("loginError", "Tên người dùng hoặc mật khẩu không chính xác.");
//            return "login/login"; // Hiển thị lại trang đăng nhập với thông báo lỗi.
//        }
//    }
//}
