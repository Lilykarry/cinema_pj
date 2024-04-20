package com.example.demo.service;

import com.example.demo.model.Movie;
import com.example.demo.model.Showtimes;
import com.example.demo.model.Threat;

import java.util.List;

public interface ShowTimeService {
    List<Threat> findAllThreats();
    List<Showtimes> findAllShowtimes(String id);

    List<Movie> showAllFilm(Integer ThreatId);

    List<Showtimes> findDistinctDates(Integer threatId, String movieId);

    List<Movie> getMoviesAndShowtimesForDate(Integer threatId, String selectedDate);

    List<Showtimes> getAvailableDatesForTheater(Integer threatId);


}
