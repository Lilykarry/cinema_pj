package com.example.demo.service.impl;

import com.example.demo.domain.UpsertMovie;
import com.example.demo.exception.MovieNotFoundExeption;
import com.example.demo.model.Movie;
import com.example.demo.repository.MovieRepository;
import com.example.demo.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.time.LocalDate;
import java.util.Date;
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
    public void save(UpsertMovie movie) throws IOException {
        Movie entity=new Movie();
        entity.setMovieId(movie.getMovieId());
        entity.setMovieTitle(movie.getMovieTitle());
        entity.setMovieCategory(movie.getMovieCategory());
        entity.setPerformer(movie.getPerformer());
        entity.setFilmDirector(movie.getFilmDirector());
        entity.setMovieContent(movie.getMovieContent());
        if (movie.getImage() != null && !movie.getImage().isEmpty()) {

            entity.setImage(generateImagePath(movie.getImage()));
        } else {
        }
        entity.setTrailer(movie.getTrailer());
        LocalDate localDate = movie.getStartMovie(); // LocalDate
        Date startDate = java.sql.Date.valueOf(localDate); // Chuyển đổi từ LocalDate sang Date
        entity.setStartMovie(startDate); // Gán Date vào entity
        LocalDate localDate1 = movie.getEndMovie(); // LocalDate
        Date endDate = java.sql.Date.valueOf(localDate1); // Chuyển đổi từ LocalDate sang Date
        entity.setEndMovie(endDate); // Gán Date vào entity

        entity.setStatus(movie.getStatus());

        movieRepository.save(entity);
    }
    private String generateImagePath(MultipartFile file) throws IOException {
        String fileExtension = getFileExtension(file.getOriginalFilename());

        File file1 = new File("/Users/LENOVO/IdeaProjects/cinema_pj/src/main/resources/static/imagesMovie/" + file.getOriginalFilename());

        try (OutputStream os = new FileOutputStream(file1)) {
            os.write(file.getBytes());
        }

        return file.getOriginalFilename();
    }

    private String getFileExtension(String originalFilename) {
        return "";
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
