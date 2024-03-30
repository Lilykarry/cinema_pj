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
    private MovieRepository homeRepository;

    @Override
    public List<Movie> showAllFilm() {
        return homeRepository.findAll();
    }
}
