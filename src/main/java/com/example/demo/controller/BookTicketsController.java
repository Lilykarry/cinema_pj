    package com.example.demo.controller;

    import com.example.demo.model.*;
    import com.example.demo.security.UserPrincipal;
    import com.example.demo.service.SeatService;
    import com.example.demo.service.TicketService;
    import com.example.demo.service.UserService;
    import com.example.demo.service.WaterCornService;
    import com.fasterxml.jackson.databind.ObjectMapper;
    import org.apache.catalina.User;
    import org.json.JSONArray;
    import org.json.JSONException;
    import org.json.JSONObject;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.http.ResponseEntity;
    import org.springframework.security.core.Authentication;
    import org.springframework.security.core.context.SecurityContextHolder;
    import org.springframework.stereotype.Controller;
    import org.springframework.ui.Model;
    import org.springframework.web.bind.annotation.*;

    import java.io.IOException;
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

        @Autowired
        private UserService userService;


        @GetMapping("/bookTicket/layGiaGhe")
        @ResponseBody
        public String getSeatPrices(@RequestParam("dsGhe") String stringArray) {
            // Check if the input string is not null and not empty
            if (stringArray != null && !stringArray.isEmpty()) {
                // Split the input string into an array of seat IDs
                String[] array = stringArray.split(",");
                // StringBuilder to store seat prices HTML
                StringBuilder seatPrices = new StringBuilder();
                // Loop through the array of seat IDs
                for (String string : array) {
                    // Retrieve seat information by ID from service
                    Seat seat = seatService.findById(Integer.parseInt(string));
                    // Append seat price information to the StringBuilder
                    seatPrices.append("<p>")
                            .append(seat.getRowId().getRowNo())
                            .append(seat.getSeatNo())
                            .append(" = ")
                            .append(seat.getPrice())
                            .append(" VNĐ</p>");
                }
                // Return seat prices HTML as a string
                return seatPrices.toString();
            } else {
                // Return a message if no seats are selected
                return "<p>[Bạn chưa chọn ghế nào cả]</p>";
            }
        }

        @PostMapping("/bookTicket/layGiaBapNuoc")
        @ResponseBody
        public String getWaterCornPrices(@RequestParam("dsBapNuoc") String stringArray) {
            // Check if the input string is not null and not empty
            if (stringArray != null && !stringArray.isEmpty()) {
                try {
                    // Parse the input JSON array string
                    JSONArray jsonArray = new JSONArray(stringArray);
                    // StringBuilder to store water corn prices HTML
                    StringBuilder waterCornPrices = new StringBuilder();
                    // Loop through the JSON array
                    for (int i = 0; i < jsonArray.length(); i++) {
                        // Get each JSON object from the array
                        JSONObject item = jsonArray.getJSONObject(i);
                        // Extract information from the JSON object
                        int id = item.getInt("id");
                        int quantity = item.getInt("quantity");
                        // Retrieve water corn information by ID from service
                        WaterCorn wc = waterCornService.findById(id);
                        // Append water corn price information to the StringBuilder
                        waterCornPrices.append("<p>")
                                .append(wc.getNameWaterCorn())
                                .append(" X ")
                                .append(quantity)
                                .append(" = ")
                                .append(wc.getPrice() * quantity)
                                .append(" VNĐ</p>");
                    }
                    // Return water corn prices HTML as a string
                    return waterCornPrices.toString();
                } catch (JSONException e) {
                    // Return an error message if there's an exception during processing
                    return "<p>[Có lỗi xảy ra trong việc xử lý dữ liệu]</p>";
                }

            } else {
                // Return a message if no water corn is selected
                return "<p>[Bạn chưa chọn bắp nước nào cả]</p>";
            }
        }

        @PostMapping("/bookTicket/tongGia")
        @ResponseBody
        public String getTotalPrice(@RequestParam("dsGhe") String stringArrayGhe,
                                    @RequestParam("dsBapNuoc") String stringArrayBapNuoc) {
            int totalPrice = 0;

            // Calculate the price of selected seats if the stringArrayGhe is not null or empty
            if (stringArrayGhe != null && !stringArrayGhe.isEmpty()) {
                String[] arrayGhe = stringArrayGhe.split(",");
                for (String string : arrayGhe) {
                    Seat seat = seatService.findById(Integer.parseInt(string));
                    totalPrice += seat.getPrice();
                }
            }

            // Calculate the price of selected water corn items if the stringArrayBapNuoc is not null or empty
            if (stringArrayBapNuoc != null && !stringArrayBapNuoc.isEmpty()) {
                try {
                    JSONArray dsBapNuoc = new JSONArray(stringArrayBapNuoc);
                    for (int i = 0; i < dsBapNuoc.length(); i++) {
                        JSONObject item = dsBapNuoc.getJSONObject(i);
                        int quantity = item.getInt("quantity");
                        int id = item.getInt("id");
                        WaterCorn wc = waterCornService.findById(id);
                        totalPrice += quantity * wc.getPrice();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            // Return the total price with the appropriate currency format
            return totalPrice + " VNĐ";
        }

        // Method to calculate the total price of selected seats
        private int calculateSeatPrices(String stringArrayGhe) {
            int totalPrice = 0;
            if (stringArrayGhe != null && !stringArrayGhe.isEmpty()) {
                String[] arrayGhe = stringArrayGhe.split(",");
                for (String string : arrayGhe) {
                    Seat seat = seatService.findById(Integer.parseInt(string));
                    totalPrice += seat.getPrice();
                }
            }
            return totalPrice;
        }


        @GetMapping("/bookTicket")
        public String showBookTicketPage(Model model, @RequestParam("mvID") String mvID, @RequestParam("day") String day, @RequestParam("time") String time){
            // Get current authenticated user
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            UserPrincipal users = (UserPrincipal) authentication.getPrincipal();
            Users user = userService.findByEmail(users.getEmail());
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            DateFormat dateFormat = new SimpleDateFormat("HH:mm");
            List<Seat> danhSachGheDaDat = new ArrayList();
            LocalDate date = LocalDate.parse(day);
            LocalTime times = LocalTime.parse(time);
            // Lấy ID của suất chiếu dựa trên ngày, thời gian và ID của phim
            int idST = ticketService.findShowtimesByDateTimeMvID(date,times,mvID).getShowtimesId();
            System.out.println("id showtime:"+idST);
            // Lấy danh sách đơn hàng dựa trên ID của suất chiếu và trạng thái đã đặt
            List<Ticket> dsDonHang = ticketService.findAllTicketsByShowtimesByStatus(idST, 1);
            for (Ticket ticket : dsDonHang) {
                // Lấy danh sách ghế đã đặt từ mỗi đơn hàng và thêm vào danh sách tổng
                for (TicketDetailsSeat tds : ticket.getTicketDetailsSeatCollection()) {
                    danhSachGheDaDat.add(tds.getSeatId());
                }
            }
            String html = "";
            // Tạo HTML hiển thị danh sách ghế
            for (RowOfSeats rowOfSeats : ticketService.findByID(idST).getRoomId().getRowOfSeatsCollection()) {
                html += "<div class='d-flex justify-content-center' id='divDSGhe'>";
                for (Seat seat : rowOfSeats.getSeatCollection()) {
                    if (danhSachGheDaDat.contains(seat)) {
                        // Nếu ghế đã đặt, disable và thêm màu cam
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
                        // Nếu ghế chưa đặt, cho phép chọn
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
            // Đưa dữ liệu vào model để hiển thị trên trang
            model.addAttribute("suatChieu", ticketService.findByID(idST));
            model.addAttribute("gio", dateFormat.format(ticketService.findByID(idST).getTime()));
            model.addAttribute("ngay", formatter.format(ticketService.findByID(idST).getDate()));
            model.addAttribute("dsBapNuoc",waterCornService.listAll());
            model.addAttribute("html", html);
            model.addAttribute("user", user);
            model.addAttribute("idST",idST);
            return "ticket/bookTicket";
        }


    }
