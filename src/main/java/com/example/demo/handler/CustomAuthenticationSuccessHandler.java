package com.example.demo.handler;

import com.example.demo.repository.AdminRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.IOException;

public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Autowired
    private AdminRepository adminRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        // Lấy tên người dùng (email hoặc tên đăng nhập) từ Authentication
        authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        // Kiểm tra xem người dùng có phải là quản trị viên không
        boolean isAdmin = isAdminUser(username);

        // Chuyển hướng người dùng dựa trên loại người dùng (quản trị viên hoặc người dùng thông thường)
        if (isAdmin) {
            response.sendRedirect("Admin/Home");
        } else {
            response.sendRedirect("guest/home");
        }
    }

    // Phương thức kiểm tra xem người dùng có phải là quản trị viên không
    private boolean isAdminUser(String username) {
        return adminRepository.existsAdminByEmail(username);
    }
}