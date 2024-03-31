package com.example.demo.controller;

import com.example.demo.model.Endow;
import com.example.demo.model.Threat;
import com.example.demo.service.PromotionService;
import com.example.demo.service.ShowTimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/guest")
public class PromotionController {
    @Autowired
    private PromotionService promotionService;
    @GetMapping("/promotion")
    public String showTime(Model model){
        List<Endow> endows = promotionService.listAll();

        model.addAttribute("endows", endows);

        return "home/promotion";
    }
}
