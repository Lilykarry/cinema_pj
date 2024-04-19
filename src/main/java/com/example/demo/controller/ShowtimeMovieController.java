package com.example.demo.controller;

import com.example.demo.model.Movie;
import com.example.demo.model.Room;
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
    public String show(@PathVariable("ThreatId") Integer threatId, Model model) {
        List<Movie> movies = showTimeService.showAllFilm(threatId);

        // Iterate through each movie
        for (Movie movie : movies) {
            List<Showtimes> movieShowtimes = new ArrayList<>(movie.getShowtimesCollection());
            List<Showtimes> filteredShowtimes = new ArrayList<>();

            // Iterate through each showtime of the current movie
            for (Showtimes showtime : movieShowtimes) {
                // Retrieve the room associated with the showtime
                Room room = showtime.getRoomId();

                // Check if the room's threat matches the provided ThreatId
                if (room != null && room.getThreatId() != null && room.getThreatId().getId().equals(threatId)) {
                    // Add the showtime to the filtered list if the threat matches
                    filteredShowtimes.add(showtime);
                }
            }

            // Sort the filtered showtimes by time
            Collections.sort(filteredShowtimes, Comparator.comparing(Showtimes::getTime));

            // Replace the showtimes collection of the current movie with the filtered and sorted list
            movie.setShowtimesCollection(filteredShowtimes);
        }

        // Now, each movie in the movies list contains only the showtimes associated with the provided ThreatId

        model.addAttribute("movie", movies);
        return "home/movieShowtime";
    }


}
