package com.example.demo.controller;

import com.example.demo.model.Threat;
import com.example.demo.service.ShowTimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/guest")
public class ShowTimeController {
@Autowired
    private ShowTimeService showTimeService;
@GetMapping("/showtime")
public String showTime(Model model){
    List<Threat> threats = showTimeService.listAll();

    model.addAttribute("threats", threats);

    return "home/showtime";
}
}
