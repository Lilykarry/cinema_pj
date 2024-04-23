package com.example.demo.controller;

import com.example.demo.model.Movie;
import com.example.demo.model.Room;
import com.example.demo.model.Showtimes;
import com.example.demo.service.ShowTimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/guest")
public class ShowtimeMovieController {
    @Autowired
    private ShowTimeService showTimeService;

@GetMapping("/showtime/{ThreatId}")
public String show(@PathVariable("ThreatId") Integer threatId, Model model) {
    // Lấy danh sách showtimes cho theater cụ thể
    List<Showtimes> showtimess = showTimeService.getAvailableDatesForTheater(threatId);

    // Lọc danh sách showtimes theo ngày
    Map<String, List<Showtimes>> groupedShowtimesByDate = showtimess.stream()
            .collect(Collectors.groupingBy(showtime -> dateToString(showtime.getDate())));

    // Lọc showtimes theo ThreatId và sắp xếp theo thời gian
    List<Movie> movies = showTimeService.showAllFilm(threatId);
    for (Movie movie : movies) {
        List<Showtimes> filteredShowtimes = movie.getShowtimesCollection().stream()
                .filter(showtime -> showtime.getRoomId() != null &&
                        showtime.getRoomId().getThreatId() != null &&
                        showtime.getRoomId().getThreatId().getId().equals(threatId))
                .sorted(Comparator.comparing(Showtimes::getTime))
                .collect(Collectors.toList());

        // Cập nhật danh sách showtimes cho movie với danh sách đã lọc
        movie.setShowtimesCollection(filteredShowtimes);
    }

    // Thêm các thuộc tính vào model
    model.addAttribute("groupedShowtimesByDate", groupedShowtimesByDate);
    model.addAttribute("movie", movies);
    model.addAttribute("threatId", threatId);

    // Trả về trang view thích hợp
    return "home/movieShowtime";
}

    private String convertDateToString(java.sql.Date sqlDate) {
        // Định dạng ngày theo kiểu yyyy-MM-dd
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        // Chuyển đổi java.sql.Date thành String
        return dateFormat.format(sqlDate);
    }
    private String dateToString(Date date) {
        // Implement the conversion logic according to your date format
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormat.format(date);
    };





}
