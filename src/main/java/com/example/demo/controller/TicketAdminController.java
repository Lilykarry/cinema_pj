package com.example.demo.controller;


import com.example.demo.model.Ticket;
import com.example.demo.service.TicketDetailsSeatService;
import com.example.demo.service.TicketDetailsWaterCornService;
import com.example.demo.service.TicketService;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/Admin")
public class TicketAdminController {

    @Autowired
    private UserService userService;

    @Autowired
    private TicketService ticketService;

    @Autowired
    private TicketDetailsSeatService ticketDetailsSeatService;

    @Autowired
    private TicketDetailsWaterCornService ticketDetailsWaterCornService;

    @GetMapping("/show")
    public String showTicket(Model model,@RequestParam(name = "keyword", required = false) String keyword){
        // Get current authenticated user
        for (Ticket ticket : ticketService.showAll()) {
            if (ticket.getStatus() == 0) {
                ticketDetailsSeatService.deleteSeatsByTicketId(ticket.getTicketId());
                ticketDetailsWaterCornService.deleteWaterCornsByTicketId(ticket.getTicketId());
            }
            model.addAttribute("listWC", ticketDetailsWaterCornService.searchWaterCornssByTicketId(ticket.getTicketId()));
        }
        ticketService.deleteTicketByStatus(0);
        model.addAttribute("listTickets",ticketService.showAllAdmin());

        return "admin/managerTicket";
    }

    @GetMapping("/detail")
    public String showTicketDetail(Model model,
                                   @RequestParam("ticketId") String ticketId){

        model.addAttribute("ticket",ticketService.findByTicketID(Integer.parseInt(ticketId)));
        model.addAttribute("listSeat",ticketDetailsSeatService.searchSeatsByTicketId(Integer.parseInt(ticketId)));
        model.addAttribute("listWC",ticketDetailsWaterCornService.searchWaterCornssByTicketId(Integer.parseInt(ticketId)));

        return "admin/detailAdmin";
    }
}
