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

//    @GetMapping("/showtime/{threatId}")
//    public String show(@PathVariable("threatId") Integer threatId, Model model, @RequestParam(value = "date", required = false) String selectedDate) {
//        // Định dạng ngày tháng
//        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
//
//        // Nếu không có ngày được chọn, sử dụng ngày hiện tại
//        if (selectedDate == null) {
//            selectedDate = LocalDate.now().format(dateFormatter);
//        }
//
//        // Chuyển đổi ngày được chọn thành LocalDate và xử lý lỗi nếu có
//        LocalDate localDate;
//        try {
//            localDate = LocalDate.parse(selectedDate, dateFormatter);
//        } catch (DateTimeParseException e) {
//            // Xử lý lỗi và sử dụng ngày hôm nay làm giá trị thay thế
//            model.addAttribute("error", "Định dạng ngày không hợp lệ. Sử dụng ngày hôm nay thay thế.");
//            localDate = LocalDate.now();
//            selectedDate = localDate.format(dateFormatter);
//        }
//
//        // Chuyển đổi LocalDate sang java.sql.Date
//        java.sql.Date sqlDate = java.sql.Date.valueOf(localDate);
//
//        // Chuyển đổi java.sql.Date thành String
//        String dateString = convertDateToString(sqlDate);
//
//        // Lấy danh sách phim và ngày có sẵn
//        List<Movie> movies = showTimeService.getMoviesAndShowtimesForDate(threatId, dateString);
//        List<Showtimes> availableDates = showTimeService.getAvailableDatesForTheater(threatId);
//
//        // Thêm ngày có sẵn và ngày đã chọn vào model
//        model.addAttribute("availableDates", availableDates);
//        model.addAttribute("selectedDate", selectedDate);
//
//        // Lọc và sắp xếp showtimes cho mỗi phim
//        for (Movie movie : movies) {
//            List<Showtimes> movieShowtimes = new ArrayList<>(movie.getShowtimesCollection());
//            List<Showtimes> filteredShowtimes = new ArrayList<>();
//
//            for (Showtimes showtime : movieShowtimes) {
//                Room room = showtime.getRoomId();
//                // Kiểm tra nếu rạp của showtime khớp với rạp được cung cấp
//                if (room != null && room.getThreatId() != null && room.getThreatId().getId().equals(threatId)) {
//                    filteredShowtimes.add(showtime);
//                }
//            }
//
//            // Sắp xếp showtimes theo thời gian
//            filteredShowtimes.sort(Comparator.comparing(Showtimes::getTime));
//            movie.setShowtimesCollection(filteredShowtimes);
//        }
//
//        // Thêm danh sách phim vào model
//        model.addAttribute("movies", movies);
//        return "home/movieShowtime";
//    }
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
