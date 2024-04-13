package com.example.demo.controller;

import com.example.demo.service.MovieReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/guest")
public class NewsController {

    @Autowired
    private MovieReviewService movieReviewService;

    @GetMapping("/news")
    public String showAllNews(Model model) {
        model.addAttribute("news",movieReviewService.getAllDESC());
        return "news/news";
    }
    @GetMapping("/news/detail")
    public String showDetails(@RequestParam("id") String id,Model model) {
        model.addAttribute("news", movieReviewService.findRvById(Integer.parseInt(id)));
        return "news/detail";
    }
}
