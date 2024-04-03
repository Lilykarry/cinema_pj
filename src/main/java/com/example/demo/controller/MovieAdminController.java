package com.example.demo.controller;

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
    public String ShowAll(Model model){
        List<Movie> movies=movieService.showAllFilm();
        model.addAttribute("movies", movies);
        return "admin/movieAdmin";
    }
@GetMapping("/Movie/New")
    public String showNewForm(Model model){
        model.addAttribute("movie",new Movie());
        model.addAttribute("pageTitle","Add new movie");
        return "admin/movieAdminInsert";
}
    @PostMapping("/Movie/Save")
    public String saveMovie(@ModelAttribute("movie") @Valid Movie movie, BindingResult bindingResult, RedirectAttributes ra) {


            movieService.save(movie);
            ra.addFlashAttribute("mess","The movie has been saved successfully");
            return "redirect:/Admin/Movie";

    }
    @GetMapping("/Movie/Edit/{movieId}")
    public String showEditForm(@PathVariable("movieId") String movieId,Model model,RedirectAttributes ra){
        try {
          Movie movies=  movieService.get(movieId);
          model.addAttribute("movies",movies);
          model.addAttribute("pageTitle","Edit User (ID:"+movieId+")");
          return "admin/movieAdminInsert";
        }catch (MovieNotFoundExeption e){
            ra.addFlashAttribute("mess","The movie has been saved successfully");
            return "redirect:/Admin/Movie";
        }

    }
    @GetMapping("/Movie/Delete/{movieId}")
    public String delete(@PathVariable("movieId")String movieId) {
        movieService.deleteMovieById(movieId);
        return "redirect:/Admin/Movie";
    }

}
