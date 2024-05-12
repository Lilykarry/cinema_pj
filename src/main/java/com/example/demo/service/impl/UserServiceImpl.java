package com.example.demo.service.impl;

import com.example.demo.model.Users;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public List<Users> showAllUsers() {
        return userRepository.findAll();
    }

    @Autowired
    //Đối tượng này cung cấp các phương thức để mã hóa và so sánh mật khẩu trong spring security
    private PasswordEncoder passwordEncoder;


    @Override
    public void register(Users user)  {
        // Encode the password before saving
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        // Save the user to the database
        userRepository.save(user);
    }


    @Override
    public List<Users> searchUserByTitle(String keyword) {
        return userRepository.findByName(keyword);
    }

    @Override
    public Users findByUsername(String username) {
        return userRepository.findUsersByEmail(username);
    }

    @Override
    public boolean authenticate(String email, String password) {
        Users user = userRepository.findUsersByEmail(email);

        if (user != null) {
            // So sánh mật khẩu đã được mã hóa
            return passwordEncoder.matches(password, user.getPassword());
        }
        return false;
    }

    @Override
    public Users findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}
