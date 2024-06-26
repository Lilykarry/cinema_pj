package com.example.demo.handler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import java.io.IOException;
import java.util.Calendar;

public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest request,
                                        HttpServletResponse response,
                                        AuthenticationException exception)
            throws IOException, ServletException {

        System.out.println(exception);
        response.setStatus(HttpStatus.UNAUTHORIZED.value());

        String jsonPayload = "{\"message\" : \"%s\", \"timestamp\" : \"%s\" }";
        response.getOutputStream().println(String.format(jsonPayload, exception.getMessage(), Calendar.getInstance().getTime()));
        response.sendRedirect("login?error=Bad credentials");
    }
}