package com.example.demo.service.impl;

import com.example.demo.exception.MovieNotFoundExeption;
import com.example.demo.model.Movie;
import com.example.demo.repository.MovieRepository;
import com.example.demo.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    @Override
    public void save(Movie movie) {
        movieRepository.save(movie);
    }
    @Override
    public Movie get(String movieId) throws MovieNotFoundExeption {
          Optional<Movie> result=movieRepository.findById(movieId);
          if(result.isPresent()){
              return result.get();
          }

          throw new MovieNotFoundExeption("Could not find any movie with ID"+movieId);
    }

    @Override
    public void deleteMovieById(String movieId) {
        movieRepository.deleteById(movieId);
    }
}
