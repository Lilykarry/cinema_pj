package com.example.demo.service.impl;

import com.example.demo.model.Users;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
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



    @Override
    public List<Users> searchUserByTitle(String keyword) {
        return userRepository.findByName(keyword);
    }

    @Override
    public Users findByUsername(String username) {
        return userRepository.findUsersByEmail(username);
    }
}
