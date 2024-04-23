package com.example.demo.controller;

import com.example.demo.domain.UpsertEndow;
import com.example.demo.domain.UpsertMovie;
import com.example.demo.exception.MovieNotFoundExeption;
import com.example.demo.model.Endow;
import com.example.demo.model.Movie;
import com.example.demo.service.EndowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.thymeleaf.util.StringUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Controller
@RequestMapping("/Admin")
public class EndowAdminController {
    @Autowired
    private EndowService endowService;
    @GetMapping("/Endow")
    public String ShowAll(Model model, @RequestParam(name = "keyword", required = false) String keyword){
        if (keyword == null||keyword.isEmpty()) {
            List<Endow> endows=endowService.showAllPromotions();
            model.addAttribute("endows", endows);
        }
        else{
//            keyword = (keyword != null) ? new String(keyword.getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8) : "";
            String Keyword = StringUtils.trim(keyword);
            List<Endow> endows=endowService.searchEndowByTitle(Keyword);
            model.addAttribute("endows", endows);
            model.addAttribute("keyword", Keyword);
        }

        return "admin/EndowAdmin";
    }
    @GetMapping("/Endow/New")
    public String showNewForm(Model model){
        model.addAttribute("endow",new Endow());
        model.addAttribute("pageTitle","Add new endows");
        return "admin/EndowAdminInsert";
    }
    @PostMapping("/Endow/Save")
    public String saveMovie(@ModelAttribute UpsertEndow endow, RedirectAttributes ra)  throws IOException {
        endowService.save(endow);
        ra.addFlashAttribute("mess","The endow has been saved successfully");
        return "redirect:/Admin/Endow";

    }
    @GetMapping("/Endow/Edit/{endowID}")
    public String showEditForm(@PathVariable("endowID") String endowId,Model model,RedirectAttributes ra){
        try {
            Endow endow=  endowService.get(endowId);
            model.addAttribute("endow",endow);
            model.addAttribute("pageTitle","Edit User (ID:"+endowId+")");
            return "admin/EndowAdminInsert";
        }catch (MovieNotFoundExeption e){
            ra.addFlashAttribute("mess","The endow has been saved successfully");
            return "redirect:/Admin/Endow";
        }

    }
    @GetMapping("/Endow/Delete/{endowId}")
    public String delete(@PathVariable("endowId")String endowId,RedirectAttributes ra) {
        endowService.deleteEndowById(endowId);
        ra.addFlashAttribute("mess","The endow has been deleted successfully");
        return "redirect:/Admin/Endow";
    }
//    @GetMapping("/Movie/Search")
//    public String searchMovies(@RequestParam("keyword") String keyword, Model model) {
//        keyword = new String(keyword.getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
//        List<Movie> searchResults = movieService.searchMoviesByTitle(keyword);
//
//        // Đưa kết quả tìm kiếm và từ khóa vào model để hiển thị trên view
//        model.addAttribute("searchResults", searchResults);
//        model.addAttribute("keyword", keyword);
//        return "admin/movieAdmin";}
}
