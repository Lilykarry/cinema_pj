package com.example.demo.controller;


import com.example.demo.model.Ticket;
import com.example.demo.model.Users;
import com.example.demo.security.UserPrincipal;
import com.example.demo.service.TicketDetailsSeatService;
import com.example.demo.service.TicketDetailsWaterCornService;
import com.example.demo.service.TicketService;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/ticket")
public class TicketController {

    @Autowired
    private UserService userService;

    @Autowired
    private TicketService ticketService;

    @Autowired
    private TicketDetailsSeatService ticketDetailsSeatService;

    @Autowired
    private TicketDetailsWaterCornService ticketDetailsWaterCornService;

    @GetMapping("/show")
    public String showTicket(Model model){
        // Get current authenticated user
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserPrincipal users = (UserPrincipal) authentication.getPrincipal();
        Users taiKhoan = userService.findByEmail(users.getEmail());
        for (Ticket ticket : ticketService.showAll()) {
            if (ticket.getStatus() == 0) {
                ticketDetailsSeatService.deleteSeatsByTicketId(ticket.getTicketId());
                ticketDetailsWaterCornService.deleteWaterCornsByTicketId(ticket.getTicketId());
            }
        }
        ticketService.deleteTicketByStatus(0);
        model.addAttribute("listTickets",ticketService.showAllByEmal(taiKhoan.getEmail()));
        return "ticket/ticket";
    }

    @GetMapping("/detail")
    public String showTicketDetail(Model model,
                                   @RequestParam("ticketId") String ticketId){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserPrincipal users = (UserPrincipal) authentication.getPrincipal();
        Users taiKhoan = userService.findByEmail(users.getEmail());
        model.addAttribute("ticket",ticketService.findByTicketID(Integer.parseInt(ticketId)));
        model.addAttribute("listSeat",ticketDetailsSeatService.searchSeatsByTicketId(Integer.parseInt(ticketId)));
        model.addAttribute("listWC",ticketDetailsWaterCornService.searchWaterCornssByTicketId(Integer.parseInt(ticketId)));
        model.addAttribute("customer",taiKhoan.getEmail());
        return "ticket/detail";
    }
}
