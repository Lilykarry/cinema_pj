package com.example.demo.handler;

import com.example.demo.repository.AdminRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.IOException;

public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Autowired
    private AdminRepository adminRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        // Lấy tên người dùng (email hoặc tên đăng nhập) từ Authentication
        String username = authentication.getName();

        // Kiểm tra xem người dùng có phải là quản trị viên không
        boolean isAdmin = isAdminUser(username);

        // Chuyển hướng người dùng dựa trên loại người dùng (quản trị viên hoặc người dùng thông thường)
        if (isAdmin) {
            response.sendRedirect("Admin/Index");
        } else {
            response.sendRedirect("guest/home");
        }
    }

    // Phương thức kiểm tra xem người dùng có phải là quản trị viên không
    private boolean isAdminUser(String username) {
        // Kiểm tra cơ sở dữ liệu để xác định xem người dùng có trong bảng Admins hay không
        // Bạn có thể sử dụng `adminRepository.existsAdminsByEmail(username)` để kiểm tra nếu username là email
        return adminRepository.existsAdminsByEmail(username);
    }
}
