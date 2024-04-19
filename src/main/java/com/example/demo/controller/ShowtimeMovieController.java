package com.example.demo.controller;

import com.example.demo.model.Movie;
import com.example.demo.model.Showtimes;
import com.example.demo.service.MovieService;
import com.example.demo.service.ShowTimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Controller
@RequestMapping("/guest")
public class ShowtimeMovieController {
    @Autowired
    private ShowTimeService showTimeService;
    @GetMapping("/movie/{ThreatId}")
    public String show(@PathVariable("ThreatId") Integer ThreatId, Model model, @RequestParam(value = "MovieId", required = false) String MovieId) {

            List<Movie> movies=showTimeService.showAllFilm(ThreatId);
            List<Showtimes> showtimes=showTimeService.findDistinctDates(ThreatId,MovieId);;
        for (Movie movie : movies) {
            List<Showtimes> showtimesList = new ArrayList<>(movie.getShowtimesCollection());

            // Sắp xếp danh sách thời gian chiếu của mỗi phim từ sớm đến muộn
            Collections.sort(showtimesList, Comparator.comparing(Showtimes::getTime));

            // Gán lại danh sách đã sắp xếp cho showtimesCollection
            movie.setShowtimesCollection(showtimesList);
        }
        model.addAttribute("dates",showtimes);
            model.addAttribute("movie",movies);
            return "home/movieShowtime";
        }


}
