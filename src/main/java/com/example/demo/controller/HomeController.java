package com.example.demo.controller;


import com.example.demo.model.Movie;
import com.example.demo.model.MovieReview;
import com.example.demo.service.EndowService;
import com.example.demo.service.MovieReviewService;
import com.example.demo.service.MovieService;
import com.example.demo.service.ThreatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.http.CacheControl;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.http.ResponseEntity;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

@Controller
@RequestMapping("/guest")
public class HomeController {

    @Autowired
    private MovieService homeService;

    @Autowired
    private EndowService endowService;

    @Autowired
    private MovieReviewService movieReviewService;

    @Autowired
    private ThreatService threatService;

    @GetMapping("/home")
    public String showHomePage(Model model){
        Date currentDate = new Date();
        List<Movie> listMovie = homeService.showAllFilm();
        List<Movie> phimDangChieu = new ArrayList<Movie>();
        List<Movie> phimSapChieu = new ArrayList<Movie>();
        for (Movie moviee : listMovie) {
            if (moviee.getStatus() == 2 && moviee.getStartMovie() != null && moviee.getEndMovie() != null) {
                if (currentDate.after(moviee.getStartMovie()) && currentDate.before(moviee.getEndMovie())) {
                    moviee.setStatus(1);
                    // Gọi phương thức để lưu thay đổi vào cơ sở dữ liệu
                    homeService.update(moviee);
                }
            }
            // Sau khi kiểm tra và cập nhật trạng thái, phân loại phim
            if (moviee.getStatus() == 1 && currentDate.before(moviee.getEndMovie())) {
                phimDangChieu.add(moviee);
            } else {
                phimSapChieu.add(moviee);
            }
        }
        model.addAttribute("dsUuDai",endowService.showAllPromotions());
        model.addAttribute("dsTinTuc",movieReviewService.showAllNews());
        model.addAttribute("dsRap",threatService.showAllTheater());
        model.addAttribute("dsPhimDangChieu", phimDangChieu);
        model.addAttribute("dsPhimSapChieu", phimSapChieu);
        return "home/main";
    }

    // test header
    @GetMapping("/header")
    public String showHeader(){
        return "layout/header";
    }
    @GetMapping("/auth/status")
    public ResponseEntity<Boolean> isAuthenticated() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        boolean isAuthenticated = authentication != null && authentication.isAuthenticated();
        return ResponseEntity.ok(isAuthenticated);
    }
}
