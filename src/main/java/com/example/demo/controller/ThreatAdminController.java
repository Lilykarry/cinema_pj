package com.example.demo.controller;

import com.example.demo.domain.UpsertMovie;
import com.example.demo.domain.UpsertThreat;
import com.example.demo.exception.MovieNotFoundExeption;
import com.example.demo.model.Movie;
import com.example.demo.model.Threat;
import com.example.demo.service.ThreatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("Admin")
public class ThreatAdminController {
    @Autowired
    private ThreatService threatService;
@GetMapping("/Threat")
    public String ShowAll(Model model){
    List<Threat> threats=threatService.showAllTheater();
    model.addAttribute("threats",threats);
    return "admin/threatAdmin";
}

@GetMapping("/Threat/New")
public String showNewForm(Model model){
    model.addAttribute("threat",new Threat());
    List<String> provinces = Arrays.asList("Hà Nội", "TP. Hồ Chí Minh", "Đà Nẵng","Quảng Nam", "Hải Phòng", "Cần Thơ", "Bình Dương", "Đồng Nai", "Khánh Hòa", "Hà Giang", "Lào Cai");
    model.addAttribute("provinces", provinces);
    model.addAttribute("pageTitle","Add new threat");
    return "admin/threatAdminInsert";
}
    @PostMapping("/Threat/Save")
    public String saveMovie(@ModelAttribute UpsertThreat threat, RedirectAttributes ra) throws IOException, MovieNotFoundExeption {
        threatService.save(threat);

        ra.addFlashAttribute("mess","The threat has been saved successfully");
        return "redirect:/Admin/Threat";

    }
    @GetMapping("/Threat/Delete/{Id}")
    public String delete(@PathVariable("Id")Integer Id, RedirectAttributes ra) {
        threatService.deleteThreatById(Id);
        ra.addFlashAttribute("mess","The threat has been deleted successfully");
        return "redirect:/Admin/Threat";
    }
    @GetMapping("/Threat/Edit/{Id}")
    public String showEditForm(@PathVariable("Id") Integer Id,Model model,RedirectAttributes ra){
        try {
            List<String> provinces = Arrays.asList("Hà Nội", "TP. Hồ Chí Minh", "Đà Nẵng","Quảng Nam", "Hải Phòng", "Cần Thơ", "Bình Dương", "Đồng Nai", "Khánh Hòa", "Hà Giang", "Lào Cai");
            model.addAttribute("provinces", provinces);
            Threat threat=  threatService.get(Id);
            model.addAttribute("threat",threat);
            model.addAttribute("pageTitle","Edit Threat (ID:"+Id+")");
            return "admin/threatAdminInsert";
        }catch (MovieNotFoundExeption e){
            ra.addFlashAttribute("mess","The threat has been saved successfully");
            return "redirect:/Admin/Threat";
        }

    }
}
