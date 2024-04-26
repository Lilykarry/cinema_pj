package com.example.demo.controller;

import com.example.demo.model.Threat;
import com.example.demo.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Controller
@RequestMapping("/guest")
public class ContactController {

   @Autowired
   private TicketService ticketService;
    @GetMapping("/contact")
    public String showTime(Model model,@RequestParam("mvID") String mvID,@RequestParam("day") String day,@RequestParam("time") String time){
//        test thử
        System.out.println("mvId:"+ mvID);
        System.out.println("day:"+ day);
        System.out.println("time:"+ time);
//        test thử
        LocalDate date = LocalDate.parse(day);
        LocalTime times = LocalTime.parse(time);
        System.out.println("showtime"+ticketService.findShowtimesByDateTimeMvID(date,times,mvID));
        return "home/contact";
    }
}
