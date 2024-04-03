package com.example.demo.service;

import com.example.demo.exception.MovieNotFoundExeption;
import com.example.demo.model.Movie;

import java.util.List;

public interface MovieService {
    List<Movie> showAllFilm();
    List<Movie> searchMoviesByTitle(String search);

    void save(Movie movie);

    Movie get(String movieId) throws MovieNotFoundExeption;

    void deleteMovieById(String movieId);
}
