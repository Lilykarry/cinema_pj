package com.example.demo.controller;

import com.example.demo.domain.UpsertMovie;
import com.example.demo.exception.MovieNotFoundExeption;
import com.example.demo.model.Movie;
import com.example.demo.service.MovieService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.thymeleaf.util.StringUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Controller
@RequestMapping("/Admin")
public class MovieAdminController {
    @Autowired
    private MovieService movieService;
    @GetMapping("/Home")
    public String index(Model model){

        return "admin/index";
    }
    @GetMapping("/Movie")
    public String ShowAll(Model model,@RequestParam(name = "keyword", required = false) String keyword){
        if (keyword == null||keyword.isEmpty()) {
            List<Movie> movies=movieService.showAllFilm();
            model.addAttribute("movies", movies);
        }
        else{
//            keyword = (keyword != null) ? new String(keyword.getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8) : "";
            String Keyword = StringUtils.trim(keyword);
            List<Movie> movies=movieService.searchMoviesByTitle(Keyword);
            model.addAttribute("movies", movies);
            model.addAttribute("keyword", Keyword);
        }

        return "admin/movieAdmin";
    }
@GetMapping("/Movie/New")
    public String showNewForm(Model model){
        model.addAttribute("movie",new Movie());
        model.addAttribute("pageTitle","Add new movie");
        return "admin/movieAdminInsert";
}
    @PostMapping("/Movie/Save")
    public String saveMovie(@ModelAttribute UpsertMovie movie, RedirectAttributes ra)  throws IOException {
        if (movieService.isEmailExists(movie.getMovieTitle())) {
            // Nếu email đã tồn tại, thêm thông báo lỗi và chuyển hướng trở lại trang đăng ký
            ra.addFlashAttribute("error", "Phim đã tồn tại.");
            return "redirect:/Admin/Movie/New";
        }
            movieService.save(movie);
            ra.addFlashAttribute("mess","The movie has been saved successfully");
            return "redirect:/Admin/Movie";
    }
    @GetMapping("/Movie/Edit/{movieId}")
    public String showEditForm(@PathVariable("movieId") String movieId,Model model,RedirectAttributes ra){
        try {
          Movie movie=  movieService.get(movieId);
          model.addAttribute("movie",movie);
          model.addAttribute("pageTitle","Edit movie (ID:"+movieId+")");
          return "admin/movieAdminInsert";
        }catch (MovieNotFoundExeption e){
            ra.addFlashAttribute("mess","The movie has been saved successfully");
            return "redirect:/Admin/Movie";
        }

    }
    @GetMapping("/Movie/Delete/{movieId}")
    public String delete(@PathVariable("movieId")String movieId,RedirectAttributes ra) {
        movieService.deleteMovieById(movieId);
        ra.addFlashAttribute("mess","The movie has been deleted successfully");
        return "redirect:/Admin/Movie";
    }
    @GetMapping("/Movie/Search")
    public String searchMovies(@RequestParam("keyword") String keyword, Model model) {
        keyword = new String(keyword.getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
        List<Movie> searchResults = movieService.searchMoviesByTitle(keyword);

        // Đưa kết quả tìm kiếm và từ khóa vào model để hiển thị trên view
        model.addAttribute("searchResults", searchResults);
        model.addAttribute("keyword", keyword);
        return "admin/movieAdmin";}


}
