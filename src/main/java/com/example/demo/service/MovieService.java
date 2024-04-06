package com.example.demo.service;

import com.example.demo.domain.UpsertMovie;
import com.example.demo.exception.MovieNotFoundExeption;
import com.example.demo.model.Movie;

import java.io.IOException;
import java.util.List;

public interface MovieService {
    List<Movie> showAllFilm();
    List<Movie> searchMoviesByTitle(String search);
    Movie findMovieById(String id);


    void save(UpsertMovie movie) throws IOException;

    Movie get(String movieId) throws MovieNotFoundExeption;

    void deleteMovieById(String movieId);
}
