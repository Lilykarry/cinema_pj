package com.example.demo.service;

import com.example.demo.model.Users;

import java.util.List;

public interface UserService {
    List<Users> showAllUsers() ;
    void register(Users user) ;


    List<Users> searchUserByTitle(String keyword);

    Users findByUsername(String username);
}
