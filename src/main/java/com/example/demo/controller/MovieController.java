package com.example.demo.controller;

import com.example.demo.model.Movie;
import com.example.demo.model.Room;
import com.example.demo.model.Showtimes;
import com.example.demo.model.Threat;
import com.example.demo.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.nio.charset.StandardCharsets;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;
import java.util.Date;


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
        model.addAttribute("theaters",movieService.getAllTheatersByMovieId(id));
        model.addAttribute("movie",movieService.findMovieById(id));
        // Get the movie and its start date

        Movie movie = movieService.findMovieById(id);
        Date startDate = movie.getStartMovie();

        // Convert the start date to a string in the desired format
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        String formattedStartDate = dateFormat.format(startDate);
        // Add the formatted start date to the model
        model.addAttribute("formattedStartDate", formattedStartDate);
        return "movie/movieDetail";
    }

    @GetMapping("/movie/details")
    public String showMovieDetails(@RequestParam("id") String id, @RequestParam("threatId") String threatId, Model model) {

        model.addAttribute("theaters",movieService.getAllTheatersByMovieId(id));
        model.addAttribute("movie",movieService.findMovieById(id));
        model.addAttribute("showtimesss",movieService.getAllShowtimes(Integer.parseInt(threatId), id));
        List<Showtimes> showtimess = movieService.getAllShowtimes(Integer.parseInt(threatId), id);
        Map<String, List<Showtimes>> groupedShowtimesByDate = showtimess.stream()
                .collect(Collectors.groupingBy(showtime -> dateToString(showtime.getDate())));

        model.addAttribute("groupedShowtimesByDate", groupedShowtimesByDate);
        model.addAttribute("threatId", threatId); // Pass theaterId to the view

        // Print out the contents of groupedShowtimesByDate map
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
        // Lặp qua từng cặp key-value trong map
        for (Map.Entry<String, List<Showtimes>> entry : groupedShowtimesByDate.entrySet()) {
            String date = entry.getKey(); // Lấy ngày từ khóa (key)
            List<Showtimes> showtimesList = entry.getValue(); // Lấy danh sách Showtimes từ giá trị (value)

            // Lặp qua từng đối tượng Showtimes trong danh sách
            for (Showtimes showtime : showtimesList) {
                Date time = showtime.getTime(); // Lấy thời gian từ mỗi đối tượng Showtimes
                // Chuyển đổi thời gian thành chuỗi
                String formattedTime = dateFormat.format(time);
            }
        }

        Movie movie = movieService.findMovieById(id);
        Date startDate = movie.getStartMovie();

        // Convert the start date to a string in the desired format
        SimpleDateFormat dateFormats = new SimpleDateFormat("dd-MM-yyyy");
        String formattedStartDate = dateFormats.format(startDate);
        // Add the formatted start date to the model
        model.addAttribute("formattedStartDate", formattedStartDate);
        return "movie/movieDetail";
    }

    // Utility methods to convert Date and Time objects to String
    private String dateToString(Date date) {
            // Implement the conversion logic according to your date format
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            return dateFormat.format(date);
    };


}
