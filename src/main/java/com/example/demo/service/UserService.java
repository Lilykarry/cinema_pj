package com.example.demo.service;

import com.example.demo.domain.UpsertUser;
import com.example.demo.exception.FieldMissMatchException;
import com.example.demo.model.Users;

import java.util.List;

public interface UserService {
    List<Users> showAllUsers() ;
    void register(Users user) ;


    List<Users> searchUserByTitle(String keyword);

    Users findByUsername(String username);

    boolean authenticate(String email, String password);
}
