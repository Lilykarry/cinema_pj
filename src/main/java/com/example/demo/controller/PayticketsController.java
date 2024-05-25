package com.example.demo.controller;

import com.example.demo.model.*;
import com.example.demo.security.UserPrincipal;
import com.example.demo.service.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@Transactional

public class PayticketsController {
    @Autowired
    private VNPAYService vnPayService;
    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private ShowTimeService showTimeService;

    @Autowired
    private UserService userService;

    @Autowired
    private TicketService ticketService;
    @Autowired
    private SeatService seatService;
    @Autowired
    private WaterCornService waterCornService;

    @Autowired
    private TicketDetailsSeatService ticketDetailsSeatService;

    @Autowired
    private TicketDetailsWaterCornService ticketDetailsWaterCornService;




    @GetMapping("/payment")
    public String show(@RequestParam("dsGhe") String dsGhe,
                       @RequestParam("dsBapNuoc") String stringArrayBapNuoc,
                       @RequestParam("idST") String idST,
                       Model model) throws JSONException {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        DateFormat dateFormat = new SimpleDateFormat("HH:mm");
        String errorMessage = "";
        // check isd showtime null ?
        if (idST == null || idST.equals("")) {
            errorMessage = "ID SUẤT CHIẾU KHÔNG CÓ DỮ LIỆU, VUI LÒNG QUAY TRỞ LẠI VÀ THỰC HIỆN ĐÚNG THAO TÁC";

        }
//         check expired Showtimes
        if (showTimeService.findShowtimesByShowtimesId(Integer.parseInt(idST)) != null) {
            Showtimes st = showTimeService.findShowtimesByShowtimesId(Integer.parseInt(idST));
            if (st.getDate().compareTo(new Date()) > 0) {
                // Showtime is in the future, continue processing
            } else if (formatter.format(st.getDate()).compareTo(formatter.format(new Date())) == 0) {
                // Showtime is today, check the time
                if (dateFormat.format(st.getTime()).compareTo(dateFormat.format(new Date())) >= 0) {
                    // Showtime is still ahead, continue processing
                } else {
                    // Showtime has passed, add message to model
                    errorMessage = "--- SUẤT CHIẾU ĐÃ QUA, VUI LÒNG QUAY TRỞ LẠI VÀ THỰC HIỆN ĐÚNG THAO TÁC ---";

                }
            } else {
                // Showtime is in the past, add message to model
                errorMessage = "--- SUẤT CHIẾU ĐÃ QUA, VUI LÒNG QUAY TRỞ LẠI VÀ THỰC HIỆN ĐÚNG THAO TÁC ---";

            }
        }
        else {
            // Showtime does not exist, add message to model
            errorMessage = "--- SUẤT CHIẾU KHÔNG TỒN TẠI, VUI LÒNG QUAY TRỞ LẠI VÀ THỰC HIỆN ĐÚNG THAO TÁC ---";
        }
        // check expired Showtimes - end

        // check full seat of showtimes - start
        Showtimes st = showTimeService.findShowtimesByShowtimesId(Integer.parseInt(idST));
        int sizeOfSeatOfShowtimes = 0;
        int sizeOfSeatTicketed = 0;

        for (RowOfSeats rowOfSeats : st.getRoomId().getRowOfSeatsCollection()) {
            for (Seat seat : rowOfSeats.getSeatCollection()) {
                sizeOfSeatOfShowtimes += 1;
            }
        }
        System.out.println("size of seat:"+sizeOfSeatOfShowtimes);
        for (Ticket ticket : st.getTicketCollection()) {
            if (ticket.getStatus() != 0) {
                for (TicketDetailsSeat ticketDetailsSeat : ticket.getTicketDetailsSeatCollection()) {
                    sizeOfSeatTicketed += 1;
                }
            }
        }

        // check full seat of showtimes - end

        List<Integer> dsGheHienTai = new ArrayList<>();
        if (dsGhe == "") {
            errorMessage = "--- DANH SÁCH GHẾ TRỐNG, VUI LÒNG QUAY TRỞ LẠI VÀ THỰC HIỆN ĐÚNG THAO TÁC ---";

        } else {
            String[] array = dsGhe.split(",");
            for (String string : array) {
                try {
                    dsGheHienTai.add(Integer.parseInt(string));
                } catch (Exception e) {
                    errorMessage = "--- ID GHẾ PHẢI LÀ SỐ, VUI LÒNG QUAY TRỞ LẠI VÀ THỰC HIỆN ĐÚNG THAO TÁC ---";
                }
            }
        }
        System.out.println("dsGheHIenTai:"+dsGheHienTai);

        List<Integer> dsIdBapNuoc = new ArrayList<Integer>();
        List<Integer> dsSoLuongBapNuoc = new ArrayList<Integer>();
        if (stringArrayBapNuoc != null && !stringArrayBapNuoc.isEmpty()) {
            try {
                JSONArray dsBapNuoc = new JSONArray(stringArrayBapNuoc);
                for (int i = 0; i < dsBapNuoc.length(); i++) {
                    JSONObject item = dsBapNuoc.getJSONObject(i);
                    int quantity = item.getInt("quantity");
                    int id = item.getInt("id");
                    dsIdBapNuoc.add(id);
                    dsSoLuongBapNuoc.add(quantity);
                    System.out.println("quantity" + quantity);
                    System.out.println("id" + id);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

//        errorMessage = "--- ID BẮP NƯỚC VÀ SỐ LƯỢNG BẮP NƯỚC PHẢI LÀ SỐ, VÀ SỐ LƯỢNG BẮP NƯỚC PHẢI CÓ KHI CÓ ID BẮP NƯỚC HOẶC NGƯỢC LẠI, VUI LÒNG QUAY TRỞ LẠI VÀ THỰC HIỆN ĐÚNG THAO TÁC ---";
//
        // Get current authenticated user
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserPrincipal users = (UserPrincipal) authentication.getPrincipal();
        Users taiKhoan = userService.findByEmail(users.getEmail());
        if (taiKhoan != null) {

            Showtimes ShowtimesHienTai = showTimeService.findShowtimesByShowtimesId(Integer.parseInt(idST));
            ;
            List<Integer> danhSachSoSanh = new ArrayList<>();
            boolean bienDemSuccess = false;
            if (dsGheHienTai.size() <= 8) {
                for (Integer integer : dsGheHienTai) {
                    Seat seatHienTai = seatService.findById(integer);
                    if (seatHienTai != null) {
                        for (RowOfSeats rowOfSeats : ShowtimesHienTai.getRoomId().getRowOfSeatsCollection()) {
                            for (Seat seat : rowOfSeats.getSeatCollection()) {
                                if (seatHienTai.getSeatId() == seat.getSeatId()) {
                                    bienDemSuccess = true;
                                    break;
                                }
                            }
                        }
                    } else {
                        errorMessage = "--- GHẾ KHÔNG TỒN TẠI, VUI LÒNG QUAY TRỞ LẠI VÀ THỰC HIỆN ĐÚNG THAO TÁC ---";

                    }
//
                    if (!danhSachSoSanh.contains(integer)) {
                        danhSachSoSanh.add(integer);
                    } else {
                        errorMessage = "--- KHÔNG ĐƯỢC CHỌN CÁC GHẾ TRÙNG NHAU, VUI LÒNG QUAY TRỞ LẠI VÀ THỰC HIỆN ĐÚNG THAO TÁC ---";
                    }
                }

                if (bienDemSuccess == false) {
                    errorMessage = "--- GHẾ KHÔNG TỒN TẠI TRONG SUẤT CHIẾU NÀY, VUI LÒNG QUAY TRỞ LẠI VÀ THỰC HIỆN ĐÚNG THAO TÁC ---";

                }

                for (Ticket ticket : ticketService.findAllTicketsByShowtimesByStatus(Integer.parseInt(idST), 1)) {
                    for (TicketDetailsSeat ticketDetailsSeat : ticket.getTicketDetailsSeatCollection()) {
                        if (dsGheHienTai.contains(ticketDetailsSeat.getSeatId().getSeatId())) {
                            errorMessage = "--- GHẾ BẠN CHỌN ĐÃ ĐƯỢC ĐẶT, VUI LÒNG QUAY TRỞ LẠI VÀ THỰC HIỆN ĐÚNG THAO TÁC ---";
                        }
                    }
                }

                Ticket newTicket = new Ticket();
                newTicket.setShowtimeId(showTimeService.findShowtimesByShowtimesId(8));
                newTicket.setUserEmail(taiKhoan);
                newTicket.setStatus(0);
                ticketService.create(newTicket);

                Integer sumPrice = 0;
                if (dsIdBapNuoc.isEmpty() && dsSoLuongBapNuoc.isEmpty()) {
                    for (Integer integer : dsGheHienTai) {
                        TicketDetailsSeat newDetailTicket = new TicketDetailsSeat(); // Create a new instance in each iteration
                        newDetailTicket.setTicketId(newTicket);
                        Seat seat = seatService.findById(integer);
                        newDetailTicket.setSeatId(seat);
                        newDetailTicket.setPrice(seat.getPrice());
                        ticketDetailsSeatService.create(newDetailTicket);
                        sumPrice += seat.getPrice();
                    }

              }else {
                    List<Integer> danhSachSS = new ArrayList<>();
                    for (Integer idBapNuoc : dsIdBapNuoc) {
                        if (waterCornService.findById(idBapNuoc) == null) {
                            errorMessage = "--- BẮP NƯỚC KHÔNG TỒN TẠI, VUI LÒNG QUAY TRỞ LẠI VÀ THỰC HIỆN ĐÚNG THAO TÁC ---";
                            if (!danhSachSS.contains(idBapNuoc)) {
                                danhSachSS.add(idBapNuoc);
                            } else {
                                errorMessage = "--- KHÔNG ĐƯỢC CHỌN CÁC BẮP NƯỚC TRÙNG NHAU, VUI LÒNG QUAY TRỞ LẠI VÀ THỰC HIỆN ĐÚNG THAO TÁC ---";
                            }
                        }
                    }
                    Integer sumSoLuong = 0;
                    for (Integer integer : dsSoLuongBapNuoc) {
                        sumSoLuong += integer;
                    }
                    if (sumSoLuong > 8) {
                        errorMessage = "--- SỐ LƯỢNG BẮP NƯỚC ĐƯỢC PHÉP CHỌN TỐI ĐA LÀ 8, VUI LÒNG QUAY TRỞ LẠI VÀ THỰC HIỆN ĐÚNG THAO TÁC ---";
                    }
                    for (Integer integer : dsGheHienTai) {
                        TicketDetailsSeat newDetailTicket = new TicketDetailsSeat(); // Create a new instance in each iteration
                        newDetailTicket.setTicketId(newTicket);
                        Seat seat = seatService.findById(integer);
                        newDetailTicket.setSeatId(seat);
                        newDetailTicket.setPrice(seat.getPrice());
                        ticketDetailsSeatService.create(newDetailTicket);
                        sumPrice += seat.getPrice();
                    }
                    if (stringArrayBapNuoc != null && !stringArrayBapNuoc.isEmpty()) {
                        try {
                            JSONArray dsBapNuoc = new JSONArray(stringArrayBapNuoc);
                            for (int i = 0; i < dsBapNuoc.length(); i++) {
                                JSONObject item = dsBapNuoc.getJSONObject(i);
                                int quantity = item.getInt("quantity");
                                int id = item.getInt("id");
                                TicketDetailsWaterCorn newWTTicket = new TicketDetailsWaterCorn(); // Create a new instance in each iteration
                                newWTTicket.setTicketId(newTicket);
                                WaterCorn waterCorn = waterCornService.findById(id);
                                newWTTicket.setIdWaterCorn(waterCorn);
                                newWTTicket.setSoLuong(quantity);
                                newWTTicket.setUnitPrice(waterCorn.getPrice());
                                ticketDetailsWaterCornService.create(newWTTicket);
                                sumPrice += (waterCorn.getPrice() * quantity);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
//
              }
                  newTicket.setTotalPrice(sumPrice);
                  ticketService.create(newTicket);
                  Ticket tickets = ticketService.findByTicketID(newTicket.getTicketId());

                  model.addAttribute("ticketHienTai", tickets);
                    System.out.println("ticket id:"+tickets.getTicketId());

                  model.addAttribute("listS", ticketDetailsSeatService.searchSeatsByTicketId(tickets.getTicketId()));
                  model.addAttribute("listWC", ticketDetailsWaterCornService.searchWaterCornssByTicketId(tickets.getTicketId()));
                  model.addAttribute("gio", dateFormat.format(tickets.getShowtimeId().getTime()));
                  model.addAttribute("ngay", formatter.format(tickets.getShowtimeId().getDate()));
                  model.addAttribute("errorMessage", errorMessage);

            }
            else{
//                huySessionGiuVe(request);
                errorMessage = "--- SỐ LƯỢNG GHẾ ĐƯỢC PHÉP CHỌN TỐI ĐA LÀ 8, VUI LÒNG QUAY TRỞ LẠI VÀ THỰC HIỆN ĐÚNG THAO TÁC ---";
            }
//
//
        }
        else {
            errorMessage = "--- BẠN PHẢI ĐĂNG NHẬP MỚI CÓ THỂ THANH TOÁN, VUI LÒNG QUAY TRỞ LẠI VÀ THỰC HIỆN ĐÚNG THAO TÁC ---";
        }
        return "ticket/payTicket";
    }
    @PostMapping("/submitOrder")
    public String submidOrder(@RequestParam("amount") int orderTotal,
                              @RequestParam("orderInfo") String orderInfo,
                              @RequestParam("ticketId") int ticketId,
                              HttpServletRequest request){
        String baseUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
        String vnpayUrl = vnPayService.createOrder(request, orderTotal, orderInfo, ticketId, baseUrl);
        return "redirect:" + vnpayUrl;
    }

    // Sau khi hoàn tất thanh toán, VNPAY sẽ chuyển hướng trình duyệt về URL này
    @GetMapping("/vnpay-payment-return")
    public String paymentCompleted(HttpServletRequest request, Model model){
        int paymentStatus =vnPayService.orderReturn(request);

        String orderInfo = request.getParameter("vnp_OrderInfo");
        String paymentTime = request.getParameter("vnp_PayDate");
        String transactionId = request.getParameter("vnp_TransactionNo");
        String totalPrice = request.getParameter("vnp_Amount");

        String ticketId = "";
        if (orderInfo != null && orderInfo.contains("TicketID: ")) {
            ticketId = orderInfo.substring(orderInfo.indexOf("TicketID: ") + 10);
        }
        Ticket ticket = ticketService.findByTicketID(Integer.parseInt(ticketId));
        if(ticket.getStatus() == 0){
            ticket.setPay(1);
            ticket.setStatus(1);
            ticket.setTicketBookingTime(new Date());

        }
        ticketService.create(ticket);

        model.addAttribute("orderId", orderInfo);
        model.addAttribute("totalPrice", totalPrice);
        model.addAttribute("paymentTime", paymentTime);
        model.addAttribute("transactionId", transactionId);
        System.out.println("payment status:"+paymentStatus);
        return paymentStatus == 1 ? "ticket/orderSuccess" : "ticket/orderFail";
    }
}

