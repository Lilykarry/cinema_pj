package com.example.demo.service.impl;

import com.example.demo.domain.UpsertMovie;
import com.example.demo.exception.MovieNotFoundExeption;
import com.example.demo.model.Movie;
import com.example.demo.model.Room;
import com.example.demo.model.Showtimes;
import com.example.demo.model.Threat;
import com.example.demo.repository.MovieRepository;
import com.example.demo.repository.RoomRepository;
import com.example.demo.repository.ShowTimeRepository;
import com.example.demo.repository.ThreatRepository;
import com.example.demo.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class MovieServiceImpl implements MovieService {

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private ThreatRepository threatRepository;

    @Autowired
    private ShowTimeRepository showTimeRepository;

    @Autowired
    private RoomRepository roomRepository;

    @Override
    public List<Movie> showAllFilm() {
        return movieRepository.findAll(Sort.by(Sort.Direction.DESC, "movieId"));
    }

    @Override
    public List<Movie> searchMoviesByTitle(String search) {
        return movieRepository.findByMovieTitle(search);
    }

    @Override
    public void save(UpsertMovie movie) throws IOException {
        String newMovieID = generateMovieID();
        Movie entity = movieRepository.findById(newMovieID).orElse(new Movie());

        if (movie.getImage() != null && !movie.getImage().isEmpty()) {
            entity.setImage(generateImagePath(movie.getImage()));
        }
        entity.setMovieId(newMovieID);
        entity.setMovieTitle(movie.getMovieTitle());
        entity.setMovieCategory(movie.getMovieCategory());
        entity.setPerformer(movie.getPerformer());
        entity.setFilmDirector(movie.getFilmDirector());
        entity.setMovieContent(movie.getMovieContent());
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

    @Override
    public void update(Movie movie) {
        movieRepository.save(movie);
    }


    public String generateMovieID() {
        String maxMovieID = movieRepository.findMaxMovieID();
        if (maxMovieID == null) {
            return "M001";
        } else {
            int lastNumber = Integer.parseInt(maxMovieID.substring(1));
            int newNumber = lastNumber + 1;
            return "M" + String.format("%03d", newNumber);
        }
    }
    private String generateImagePath(MultipartFile file) throws IOException {
        String fileExtension = getFileExtension(file.getOriginalFilename());

        File file1 = new File("/Users/LENOVO/IdeaProjects/cinama_PJ_IMG/imagesMovie/" + file.getOriginalFilename());

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

    @Override
    public List<Movie> searchMovies(String keyword) {
        return movieRepository.findByTitleContaining(keyword);
    }

    @Override
    public void deleteShowtimeById(Integer showtimeId) {
        showTimeRepository.deleteById(showtimeId);
    }

    @Override
    public boolean isEmailExists(String movieTitle) {
        return movieRepository.existsByMovieTitle(movieTitle);
    }

    @Override
    public List<Threat> getAllTheatersByMovieId(String id) {
        return threatRepository.findAllByMovieId(id);
    }

    @Override
    public List<Showtimes> getAllByMovieId(String id) {
        return showTimeRepository.findShowtimesByMovieId_MovieId(id);
    }

    @Override
    public List<Showtimes> getAllShowtimes(Integer threatId, String movieId) {
        return showTimeRepository.findShowtimesByThreatIdAndMovieId(threatId,movieId);
    }

    @Override
    public Movie findMovieById(String id) {
        return movieRepository.findByMovieId(id);
    }
}
