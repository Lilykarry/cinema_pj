package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/guest")
public class BookTicketsController {

    @GetMapping("/bookTicket")
    public String showBookTicketPage(Model model){
        return "ticket/bookTicket";
    }
}
