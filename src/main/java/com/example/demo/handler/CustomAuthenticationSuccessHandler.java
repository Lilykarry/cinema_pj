package com.example.demo.handler;

import com.example.demo.model.Admins;
import com.example.demo.model.Users;
import com.example.demo.repository.AdminRepository;
import com.example.demo.security.UserPrincipal;
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
        authentication = SecurityContextHolder.getContext().getAuthentication();
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        String userEmail = userPrincipal.getEmail();

        // Kiểm tra xem người dùng là quản trị viên hay không
        boolean isAdmin = isAdminUser(userEmail);

        // Chuyển hướng người dùng dựa trên loại người dùng (quản trị viên hoặc người dùng thông thường)
        if (isAdmin) {
            response.sendRedirect("/Admin/Home");
        } else {
            response.sendRedirect("/guest/home");
        }
    }

    private boolean isAdminUser(String email) {
        Admins admin = adminRepository.findByEmail(email);
        return admin != null;
    }
}