package com.example.demo.service.impl;

import com.example.demo.model.Movie;
import com.example.demo.repository.MovieRepository;
import com.example.demo.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovieServiceImpl implements MovieService {

    @Autowired
    private MovieRepository movieRepository;

    @Override
    public List<Movie> showAllFilm() {
        return movieRepository.findAll();
    }

    @Override
    public List<Movie> searchMoviesByTitle(String search) {
        return movieRepository.findByMovieTitle(search);
    }
}
