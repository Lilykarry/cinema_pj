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
import java.util.*;
import java.util.stream.Collectors;

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
//        List<Threat> threats = movieService.getAllTheatersByMovieId(id);
//        List<Showtimes> sts = movieService.getAllByMovieId(id);
//        Set<String> uniqueTheaterDate = new HashSet<>();
//
//        for (Threat threat : threats) {
//            System.out.println("Threat: " + threat.getName());
//            List<Room> rooms = new ArrayList<>(threat.getRooms());
//            for (Room room : rooms) {
//                System.out.println("Room: " + room.getRoomId());
//                List<Showtimes> filteredShowtimes = sts.stream()
//                        .filter(st -> st.getRoomId().getRoomId().equals(room.getRoomId()) && st.getMovieId().getMovieId().equals(id))
//                        .collect(Collectors.toList());
//                for (Showtimes show : filteredShowtimes) {
//                    String theaterDateKey = room.getRoomId() + "_" + show.getDate().toString();
//                    if (!uniqueTheaterDate.contains(theaterDateKey)) {
//                        System.out.println("Day: " + show.getDate());
//                        uniqueTheaterDate.add(theaterDateKey);
//                    }
//                    System.out.println("Showtime: " + show.getTime());
//                    System.out.println("**********");
//                }
//            }
//            System.out.println("#########");
//            uniqueTheaterDate.clear();
//        }
        return "movie/movieDetail";
    }

    @GetMapping("/movie/detail/showtime")
    public String showMovieDetails(@RequestParam("id") String id, @RequestParam("threatId") String threatId, Model model) {
//        model.addAttribute("showtimes",movieService.getAllShowtimesByMovieAndTheater(id,Integer.parseInt(threatId)));
//        List<Showtimes> ss = movieService.getAllShowtimesByMovieAndTheater(id,Integer.parseInt(threatId));
//        for(Showtimes s : ss){
//            System.out.println("date"+ s.getDate());
//        }
//        model.addAttribute("theaters",movieService.getAllTheatersByMovieId(id));
//        model.addAttribute("movie",movieService.findMovieById(id));
//        System.out.println(id);
//        System.out.println("threatId"+threatId);
//        for(Showtimes a : movieService.getAllShowtimes(Integer.parseInt(threatId),id)){
//            System.out.println("time"+ a.getTime());
//        }
        model.addAttribute("theaters",movieService.getAllTheatersByMovieId(id));
        model.addAttribute("movie",movieService.findMovieById(id));
        List<Showtimes> showtimes = movieService.getAllShowtimes(Integer.parseInt(threatId), id);
        if (showtimes.isEmpty()) {
            System.out.println("No showtimes found for threatId: " + threatId + " and movieId: " + id);
        } else {
            for (Showtimes showtime : showtimes) {
                if (showtime.getTime() == null) {
                    System.out.println("Showtime with ID " + showtime.getShowtimesId() + " has null time.");
                } else {
                    System.out.println("Day for showtime with movieID " + showtime.getMovieId() + ": " + showtime.getDate());
                    System.out.println("Time: " + showtime.getShowtimesId() + ": " + showtime.getTime());
                    System.out.println("************");
                }
            }
        }

        return "movie/movieDetail";
    }

}
