package com.example.demo.config;

import com.example.demo.handler.CustomAuthenticationFailureHandler;
import com.example.demo.handler.CustomAuthenticationSuccessHandler;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;

import java.io.IOException;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/static/**").permitAll()
                        .requestMatchers("/login","/register","/guest/**").permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .loginProcessingUrl("/authentication")
                        .successHandler(authenticationSuccessHandler())
                        .failureHandler(authenticationFailureHandler()))
                .sessionManagement(session -> session
                         .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)

                        )
                        // Cấu hình đăng xuất
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessHandler(logoutSuccessHandler())
                );


        return http.build();
    }


    @Bean
    public CustomAuthenticationSuccessHandler authenticationSuccessHandler() {
        return new CustomAuthenticationSuccessHandler();
    }

    @Bean
    public CustomAuthenticationFailureHandler authenticationFailureHandler() {
        return new CustomAuthenticationFailureHandler();
    }
    @Bean
    public LogoutSuccessHandler logoutSuccessHandler() {
        return new SimpleUrlLogoutSuccessHandler() {
            @Override
            public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
                // Lấy URL của trang trước đó
                String refererUrl = request.getHeader("Referer");
                System.out.println("Referer URL: " + refererUrl); // Thêm thông báo logging

                if (refererUrl != null && !refererUrl.isEmpty()) {
                    // Chuyển hướng người dùng về trang trước đó sau khi đăng xuất thành công
                    response.sendRedirect(refererUrl);
                } else {
                    // Nếu không có URL trước đó, giữ nguyên người dùng trên trang chủ hoặc trang hiện tại
                    // Lựa chọn giữ người dùng ở lại trang hiện tại sau khi đăng xuất
                    response.sendRedirect("/");
                }
            }
        };
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}