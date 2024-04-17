package com.example.demo.service;

import com.example.demo.model.Movie;
import com.example.demo.model.Threat;

import java.util.List;

public interface ShowTimeService {
    List<Threat> findAllThreats();

    List<Movie> showAllFilm(Integer ThreatId);

}
