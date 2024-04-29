    package com.example.demo.controller;

    import com.example.demo.model.*;
    import com.example.demo.service.SeatService;
    import com.example.demo.service.TicketService;
    import com.example.demo.service.WaterCornService;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.http.ResponseEntity;
    import org.springframework.stereotype.Controller;
    import org.springframework.ui.Model;
    import org.springframework.web.bind.annotation.GetMapping;
    import org.springframework.web.bind.annotation.PostMapping;
    import org.springframework.web.bind.annotation.RequestMapping;
    import org.springframework.web.bind.annotation.RequestParam;

    import java.text.DateFormat;
    import java.text.SimpleDateFormat;
    import java.time.LocalDate;
    import java.time.LocalTime;
    import java.util.ArrayList;
    import java.util.HashMap;
    import java.util.List;
    import java.util.Map;

    @Controller
    @RequestMapping("/guest")
    public class BookTicketsController {

        @Autowired
        private TicketService ticketService;
        @Autowired
        private SeatService seatService;
        @Autowired
        private WaterCornService waterCornService;

        @GetMapping("/bookTicket")
        public String showBookTicketPage(Model model, @RequestParam("mvID") String mvID, @RequestParam("day") String day, @RequestParam("time") String time){
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            DateFormat dateFormat = new SimpleDateFormat("HH:mm");
            List<Seat> danhSachGheDaDat = new ArrayList();
            LocalDate date = LocalDate.parse(day);
            LocalTime times = LocalTime.parse(time);
            int idST = ticketService.findShowtimesByDateTimeMvID(date,times,mvID).getShowtimesId();
            System.out.println("id showtime:"+idST);
            List<Ticket> dsDonHang = ticketService.findAllTicketsByShowtimesByStatus(idST, 1);
            for (Ticket ticket : dsDonHang) {
                for (TicketDetailsSeat tds : ticket.getTicketDetailsSeatCollection()) {
                    danhSachGheDaDat.add(tds.getSeatId());
                }
            }
            String html = "";
            for (RowOfSeats rowOfSeats : ticketService.findByID(idST).getRoomId().getRowOfSeatsCollection()) {
                html += "<div class='d-flex justify-content-center' id='divDSGhe'>";
                for (Seat seat : rowOfSeats.getSeatCollection()) {
                    if (danhSachGheDaDat.contains(seat)) {
                        if (seat.getType() == 1) {
                            html += "<button class='gheThuong' value='" + seat.getSeatId() + "' disabled "
                                    + "style='background-color: #FFA500; color: white;'>"
                                    + seat.getRowId().getRowNo() + seat.getSeatNo()
                                    + "</button>";
                        } else if (seat.getType() == 2) {
                            html += "<button class='gheVip' value='" + seat.getSeatId() + "' disabled "
                                    + "style='background-color: #FFA500; color: white;'>"
                                    + seat.getRowId().getRowNo() + seat.getSeatNo()
                                    + "</button>";
                        } else if (seat.getType() == 3) {
                            html += "<button class='gheDoi' value='" + seat.getSeatId() + "' disabled "
                                    + "style='background-color: #FFA500; color: white;'>"
                                    + seat.getRowId().getRowNo() + seat.getSeatNo()
                                    + "</button>";
                        }
                    } else {
                        if (seat.getType() == 1) {
                            html += "<button class='gheThuong' value='" + seat.getSeatId() + "'>\n"
                                    + seat.getRowId().getRowNo() + seat.getSeatNo()
                                    + "</button>";
                        } else if (seat.getType() == 2) {
                            html += "<button class='gheVip' value='" + seat.getSeatId() + "'>\n"
                                    + seat.getRowId().getRowNo() + seat.getSeatNo()
                                    + "</button>";
                        } else if (seat.getType() == 3) {
                            html += "<button class='gheDoi' value='" + seat.getSeatId() + "'>\n"
                                    + seat.getRowId().getRowNo() + seat.getSeatNo()
                                    + "</button>";
                        }
                    }
                }
                html += "</div>";
            }
            model.addAttribute("suatChieu", ticketService.findByID(idST));
            model.addAttribute("gio", dateFormat.format(ticketService.findByID(idST).getTime()));
            model.addAttribute("ngay", formatter.format(ticketService.findByID(idST).getDate()));
            model.addAttribute("html", html);
            return "ticket/bookTicket";
        }
        @PostMapping("/getTotalPrice")
        public ResponseEntity<Map<String, Object>> getTotalPrice(
                @RequestParam("dsGhe") String stringArrayGhe,
                @RequestParam("dsBapNuoc") String stringArrayBapNuoc) {

            int totalPrice = 0;

            if (stringArrayGhe != null && !stringArrayGhe.isEmpty()) {
                String[] arrayGhe = stringArrayGhe.split(",");
                for (String seatId : arrayGhe) {
                    Seat seat = seatService.findById(Integer.parseInt(seatId));
                    totalPrice += seat.getPrice();
                }
            }

            if (stringArrayBapNuoc != null && !stringArrayBapNuoc.isEmpty()) {
                String[] arrayBapNuoc = stringArrayBapNuoc.split(";");
                for (String snack : arrayBapNuoc) {
                    String[] mang = snack.split(",");
                    int waterCornId = Integer.parseInt(mang[0]);
                    int quantity = Integer.parseInt(mang[1]);
                    WaterCorn waterCorn = waterCornService.findById(waterCornId);
                    totalPrice += quantity * waterCorn.getPrice();
                }
            }

            Map<String, Object> response = new HashMap<>();
            response.put("totalPrice", totalPrice);
            System.out.println(totalPrice);
            return ResponseEntity.ok(response);
        }

    }
