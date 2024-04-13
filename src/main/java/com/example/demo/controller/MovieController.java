package com.example.demo.controller;

import com.example.demo.model.Movie;
import com.example.demo.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/guest")
public class MovieController {

    @Autowired
    private MovieService movieService;



    @GetMapping("/movie")
    public String showAllMovie(Model model){
        List<Movie> listMovie = movieService.showAllFilm();
        List<Movie> phimDangChieu = new ArrayList<Movie>();
        List<Movie> phimSapChieu = new ArrayList<Movie>();
        for (Movie movie : listMovie) {
            if (movie.getStatus() == 1) {
                phimDangChieu.add(movie);
            }
            else {
                phimSapChieu.add(movie);
            }
        }
        model.addAttribute("dsPhimDangChieu", phimDangChieu);
        model.addAttribute("dsPhimSapChieu", phimSapChieu);
        return "home/movie";
    }

    @GetMapping("/searchView")
    public String searchView(@RequestParam("search") String search, Model model) {
        search = new String(search.getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
        if (search.trim().equals("")) {
            model.addAttribute("listSearch", new ArrayList<>());
        } else {
            List<Movie> listSearch = movieService.searchMoviesByTitle(search);
            model.addAttribute("listSearch", listSearch);
        }
        return "movie/movieSearch";
    }

    @GetMapping("/movie/detail")
    public String showMovieDetail(@RequestParam("id") String id, Model model) {

        model.addAttribute("movie",movieService.findMovieById(id));
        return "movie/movieDetail";
    }
}
