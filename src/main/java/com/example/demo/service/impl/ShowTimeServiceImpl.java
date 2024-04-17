package com.example.demo.service.impl;

import com.example.demo.model.Movie;
import com.example.demo.model.Showtimes;
import com.example.demo.model.Threat;
import com.example.demo.repository.ShowTimeRepository;
import com.example.demo.repository.ThreatRepository;
import com.example.demo.service.ShowTimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShowTimeServiceImpl implements ShowTimeService {
    @Autowired
    private ShowTimeRepository showTimeRepository;

    @Autowired
    private ThreatRepository threatRepository;

    @Override
    public List<Threat> findAllThreats() {

       return threatRepository.findAll();
    }

//    @Override
//    public List<Showtimes> findAllShowtimes(String id) {
//        return null;
//    }

    @Override
    public List<Showtimes> findAllShowtimes(String id) {
        return showTimeRepository.findShowtimesByMovieId_MovieId(id);
    }

    @Override
    public List<Movie> showAllFilm(Integer threatId ) {
        return showTimeRepository.findMoviesByThreatId(threatId);
    }
}
