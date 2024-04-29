package com.example.demo.controller;

import com.example.demo.model.Room;
import com.example.demo.model.Threat;
import com.example.demo.repository.ThreatRepository;
import com.example.demo.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("Admin")
public class RoomAdminController {
    @Autowired
    private RoomService roomService;

    @Autowired
    private ThreatRepository threatRepository;

    @GetMapping("/Room/New/{Id}")
    public String showNewForm(@PathVariable("Id") Integer Id, Model model) {
        model.addAttribute("room", new Room());
        model.addAttribute("pageTitle", "Add new room");
        model.addAttribute("threatId", Id);
        return "admin/roomAdminInsert";
    }

    @PostMapping("/Room/Save")
    public String saveRoom(@ModelAttribute Room room, @RequestParam(value = "threatId", required = true) Integer threatId, RedirectAttributes ra) {
            roomService.save(room);
            ra.addFlashAttribute("mess", "The room has been saved successfully");
        return "redirect:/Admin/Threat";
    }
}
