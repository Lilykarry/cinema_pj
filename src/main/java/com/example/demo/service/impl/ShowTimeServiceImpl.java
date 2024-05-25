package com.example.demo.service.impl;

import com.example.demo.domain.UpsertShowtime;
import com.example.demo.exception.MovieNotFoundExeption;
import com.example.demo.model.Movie;
import com.example.demo.model.Showtimes;
import com.example.demo.model.Threat;
import com.example.demo.repository.ShowTimeRepository;
import com.example.demo.repository.ThreatRepository;
import com.example.demo.service.ShowTimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Timer;

@Service
public class ShowTimeServiceImpl implements ShowTimeService {
    @Autowired
    private ShowTimeRepository  showTimeRepository;

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

    @Override
    public List<Showtimes> findDistinctDates(Integer threatId, String movieId) {
        return showTimeRepository.findShowtimesByThreatIdAndMovieId(threatId,movieId);
    }

    @Override
    public List<Movie> getMoviesAndShowtimesForDate(Integer threatId, String selectedDate) {
        return showTimeRepository.findMoviesAndShowtimesForDate(threatId,selectedDate);
    }

    @Override
    public List<Showtimes> getAvailableDatesForTheater(Integer threatId) {
        return showTimeRepository.findAvailableDatesForTheater(threatId);
    }

    @Override
    public Showtimes findShowtimesByShowtimesId(int id) {
        return showTimeRepository.findShowtimesByShowtimesId(id);
    }

    @Override
    public List<Showtimes> showAll() {
        return showTimeRepository.findAll(Sort.by(Sort.Direction.DESC, "showtimesId"));
    }

    @Override
    public List<Showtimes> searchByTitle(String keyword) {
        return showTimeRepository.findShowtimesByMovieTitle(keyword);
    }

    @Override
    public void save(UpsertShowtime showtime) throws IOException, MovieNotFoundExeption {

        Showtimes entity;

        // Kiểm tra ID của UpsertShowtime
        if (showtime.getShowtimeId() != null) {
            // Nếu ID không phải là null, kiểm tra thực thể hiện có trong cơ sở dữ liệu
            Optional<Showtimes> optionalShowtime = showTimeRepository.findById(showtime.getShowtimeId());
            if (optionalShowtime.isPresent()) {
                // Nếu thực thể hiện có, sử dụng thực thể đó
                entity = optionalShowtime.get();
            } else {
                // Nếu không tìm thấy thực thể, ném ngoại lệ
                throw new MovieNotFoundExeption("Không tìm thấy Showtimes với ID " + showtime.getShowtimeId());
            }
        } else {
            // Nếu ID là null, tạo một thực thể mới
            entity = new Showtimes();
            // Để cơ sở dữ liệu tự động tăng ID, không cần tạo ID thủ công
        }

        // Cập nhật các giá trị của thực thể từ UpsertShowtime
        if (showtime.getRoomId() != null) {
            entity.setRoomId(showtime.getRoomId());
        }
        if (showtime.getMovieId() != null) {
            entity.setMovieId(showtime.getMovieId());
        }
        if (showtime.getDate() != null) {
            Date startDate = java.sql.Date.valueOf(showtime.getDate());
            entity.setDate(startDate);
        }
        if (showtime.getTime() != null) {
            Time startTime = java.sql.Time.valueOf(showtime.getTime());
            entity.setTime(startTime);
        }

        // Lưu thực thể vào cơ sở dữ liệu
        showTimeRepository.save(entity);
    }



    @Override
    public Showtimes get(Integer showtimeId) throws MovieNotFoundExeption {
        Optional<Showtimes> result=showTimeRepository.findById(showtimeId);
        if(result.isPresent()){
            return result.get();
        }
        throw new MovieNotFoundExeption("Could not find any movie with ID"+showtimeId);
    }

    @Override
    public boolean isShowtimeOverlap(UpsertShowtime showtime) {
        List<Showtimes> existingShowtimes = showTimeRepository.findByRoomIdAndDateAndTime(
                showtime.getRoomId(),
                java.sql.Date.valueOf(showtime.getDate()),
                java.sql.Time.valueOf(showtime.getTime()));

        // Kiểm tra xem có showtime nào khác trùng lặp không
        for (Showtimes existingShowtime : existingShowtimes) {
            if (!existingShowtime.getShowtimesId().equals(showtime.getShowtimeId())) {
                return true;
            }
        }
        return false;
    }


}
