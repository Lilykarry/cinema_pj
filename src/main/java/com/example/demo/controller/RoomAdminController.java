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
        return "admin/roomAdminInsert";
    }

    @PostMapping("/Room/Save")
    public String saveRoom(@ModelAttribute Room room, @RequestParam(value = "threatId", required = true) Integer threatId, RedirectAttributes ra) {
        // Tìm Threat bằng ThreatRepository dựa trên threatId
        Threat threat = threatRepository.findById(threatId).orElse(null);

        // Kiểm tra xem Threat có tồn tại không
        if (threat != null) {
            // Gán Threat vào Room
            room.setThreatId(threat);

            // Lưu Room vào cơ sở dữ liệu
            roomService.save(room);

            // Thêm thông báo vào RedirectAttributes
            ra.addFlashAttribute("message", "The room has been saved successfully");
        } else {
            // Nếu không tìm thấy Threat, thông báo lỗi
            ra.addFlashAttribute("message", "Threat not found");
        }

        // Chuyển hướng đến trang danh sách Threat
        return "redirect:/Admin/Threat";
    }
}
