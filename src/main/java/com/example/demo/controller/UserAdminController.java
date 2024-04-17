package com.example.demo.controller;

import com.example.demo.model.Movie;
import com.example.demo.model.Users;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.thymeleaf.util.StringUtils;

import java.util.List;

@Controller
@RequestMapping("/Admin")
public class UserAdminController {
    @Autowired
    private UserService userService;
    @GetMapping("/Users")
    public String ShowAll(@RequestParam(name = "username", required = false) String username,Model model){
        if (username == null||username.isEmpty()) {
            List<Users> usersList = userService.showAllUsers();
            model.addAttribute("usersList", usersList);
        }
         else{
//            keyword = (keyword != null) ? new String(keyword.getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8) : "";
            String Keyword = StringUtils.trim(username);
            List<Users> usersList=userService.searchUserByTitle(Keyword);
            model.addAttribute("usersList", usersList);
            model.addAttribute("keyword", Keyword);
        }
        return "admin/user";
    }



}
