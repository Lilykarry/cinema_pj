package com.example.demo.controller;

import com.example.demo.domain.UpsertShowtime;
import com.example.demo.domain.UpsertThreat;
import com.example.demo.exception.MovieNotFoundExeption;
import com.example.demo.model.Movie;
import com.example.demo.model.Room;
import com.example.demo.model.Showtimes;
import com.example.demo.model.Threat;
import com.example.demo.service.MovieService;
import com.example.demo.service.RoomService;
import com.example.demo.service.ShowTimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.thymeleaf.util.StringUtils;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/Admin")
public class ShowtimeAdminController {
    @Autowired
    private ShowTimeService showTimeService;
    @Autowired
    private MovieService movieService;
    @Autowired
    private RoomService roomService;
    @GetMapping("/Showtime")
    public String showAll(Model model,@RequestParam(name = "keyword", required = false) String keyword){
        if (keyword == null||keyword.isEmpty()) {
            List<Showtimes> showtimes=showTimeService.showAll();
            model.addAttribute("showtimes", showtimes);
        }
        else{
//            keyword = (keyword != null) ? new String(keyword.getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8) : "";
            String Keyword = StringUtils.trim(keyword);
            List<Showtimes> showtimes=showTimeService.searchByTitle(Keyword);
            model.addAttribute("showtimes", showtimes);
            model.addAttribute("keyword", Keyword);
        }
        return"admin/showtimeAdmin";
    }
    @GetMapping("/Showtime/New")
    public String showNewForm(Model model){
        model.addAttribute("showtime",new Showtimes());
        List<Movie> movies=movieService.showAllFilm();
        List<Room> rooms=roomService.showAll();
        model.addAttribute("movies", movies);
        model.addAttribute("rooms", rooms);
        model.addAttribute("pageTitle","Add new Showtime");
        return "admin/showtimeAdminInsert";
    }
    @PostMapping("/Showtime/Save")
    public String saveShowtime(@ModelAttribute UpsertShowtime showtime, RedirectAttributes ra) throws IOException, MovieNotFoundExeption {
        showTimeService.save(showtime);

        ra.addFlashAttribute("mess","The threat has been saved successfully");
        return "redirect:/Admin/Showtime";

    }
    @GetMapping("/Showtime/Edit/{showtimeId}")
    public String showEditForm(@PathVariable("showtimeId") Integer showtimeId,Model model,RedirectAttributes ra){
        try {
            List<Movie> movies=movieService.showAllFilm();
            List<Room> rooms=roomService.showAll();
            model.addAttribute("movies", movies);
            model.addAttribute("rooms", rooms);
            Showtimes showtime=  showTimeService.get(showtimeId);
            model.addAttribute("showtime",showtime);

            model.addAttribute("pageTitle","Edit Showtime (ID:"+showtimeId+")");
            return "admin/showtimeAdminInsert";
        }catch (MovieNotFoundExeption e){
            ra.addFlashAttribute("mess","The showtime has been saved successfully");
            return "redirect:/Admin/Showtime";
        }

    }
    @GetMapping("/Showtime/Delete/{showtimeId}")
    public String delete(@PathVariable("showtimeId")Integer showtimeId,RedirectAttributes ra) {
        movieService.deleteShowtimeById(showtimeId);
        ra.addFlashAttribute("mess","The movie has been deleted successfully");
        return "redirect:/Admin/Showtime";
    }
}
