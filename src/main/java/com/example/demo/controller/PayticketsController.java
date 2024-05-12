package com.example.demo.controller;

import com.example.demo.model.*;
import com.example.demo.security.UserPrincipal;
import com.example.demo.service.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@Transactional
@RequestMapping("/guest")
public class PayticketsController {

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
        } else {
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
                newTicket.setShowtimeId(ShowtimesHienTai);
                newTicket.setUserEmail(taiKhoan);
                newTicket.setStatus(0);
                ticketService.create(newTicket);
                entityManager.flush();
                entityManager.clear();
                for(Ticket a : ticketService.showAll()){
                    System.out.println("ticketid: "+a.getTicketId());
                    System.out.println("showtimeId"+ a.getShowtimeId().getShowtimesId());
//                    System.out.println("time"+ a.getTicketBookingTime());
//                    System.out.println("email"+ a.getUserEmail().getEmail());

                }
//                System.out.println("roomId" +ticketService.findByID(newTicket.getTicketId()).getRoomId());
                Ticket persistedTicket = ticketService.findByTicketID(newTicket.getTicketId());
                if (persistedTicket != null) {
                    TicketDetailsSeat newDetailTicket = new TicketDetailsSeat();

                    Integer sumPrice = 0;
                    if (dsIdBapNuoc.isEmpty() && dsSoLuongBapNuoc.isEmpty()) {
                        for (Integer integer : dsGheHienTai) {
                            newDetailTicket.setTicketId(newTicket);
                            newDetailTicket.setSeatId(seatService.findById(integer));
                            newDetailTicket.setPrice(seatService.findById(integer).getPrice());
                            ticketDetailsSeatService.create(newDetailTicket);
                            sumPrice += seatService.findById(integer).getPrice();
                        }
                    } else {
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

                            Integer sumSoLuong = 0;
                            for (Integer integer : dsSoLuongBapNuoc) {
                                sumSoLuong += integer;
                            }
                            if (sumSoLuong > 8) {
                                errorMessage = "--- SỐ LƯỢNG BẮP NƯỚC ĐƯỢC PHÉP CHỌN TỐI ĐA LÀ 8, VUI LÒNG QUAY TRỞ LẠI VÀ THỰC HIỆN ĐÚNG THAO TÁC ---";
                            }

                            for (Integer integer : dsGheHienTai) {
                                newDetailTicket.setTicketId(newTicket);
                                newDetailTicket.setSeatId(seatService.findById(integer));
                                newDetailTicket.setPrice(seatService.findById(integer).getPrice());
                                ticketDetailsSeatService.create(newDetailTicket);
                                sumPrice += seatService.findById(integer).getPrice();
                            }


                            TicketDetailsWaterCorn newWTTicket = new TicketDetailsWaterCorn();

                            if (stringArrayBapNuoc != null && !stringArrayBapNuoc.isEmpty()) {
                                try {
                                    JSONArray dsBapNuoc = new JSONArray(stringArrayBapNuoc);
                                    for (int i = 0; i < dsBapNuoc.length(); i++) {

                                        JSONObject item = dsBapNuoc.getJSONObject(i);
                                        int quantity = item.getInt("quantity");
                                        int id = item.getInt("id");
                                        newWTTicket.setTicketId(newTicket);
                                        newWTTicket.setIdWaterCorn(waterCornService.findById(id));
                                        newWTTicket.setSoLuong(quantity);
                                        newWTTicket.setUnitPrice(waterCornService.findById(id).getPrice());
                                        ticketDetailsWaterCornService.create(newWTTicket);
                                        sumPrice += (waterCornService.findById(id).getPrice()
                                                * quantity);

                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }

                        }
                        newTicket.setTotalPrice(sumPrice);
                        ticketService.create(newTicket);

                        Ticket tickets = ticketService.findByTicketID(newTicket.getTicketId());

                        model.addAttribute("ticketHienTai", tickets);


                        model.addAttribute("listS", ticketDetailsSeatService.searchSeatsByTicketId(tickets.getTicketId()));
                        model.addAttribute("listWC", ticketDetailsWaterCornService.searchWaterCornssByTicketId(tickets.getTicketId()));
                        model.addAttribute("gio", dateFormat.format(tickets.getShowtimeId().getTime()));
                        model.addAttribute("ngay", formatter.format(tickets.getShowtimeId().getDate()));
                        //
                    }
                }else{
                    errorMessage = "--- ERROR: TICKET NOT SAVED TO DATABASE ---";
                }
            } else{
//                huySessionGiuVe(request);
                errorMessage = "--- SỐ LƯỢNG GHẾ ĐƯỢC PHÉP CHỌN TỐI ĐA LÀ 8, VUI LÒNG QUAY TRỞ LẠI VÀ THỰC HIỆN ĐÚNG THAO TÁC ---";
            }


        }else {
            errorMessage = "--- BẠN PHẢI ĐĂNG NHẬP MỚI CÓ THỂ THANH TOÁN, VUI LÒNG QUAY TRỞ LẠI VÀ THỰC HIỆN ĐÚNG THAO TÁC ---";
        }
//
//                Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        UserPrincipal users = (UserPrincipal) authentication.getPrincipal();
//        Users taiKhoan = userService.findByEmail(users.getEmail());
//                Ticket newTicket = new Ticket();
//                newTicket.getShowtimeId().setShowtimesId(8);
//                newTicket.setUserEmail(taiKhoan);
//                newTicket.setStatus(0);
//                ticketService.create(newTicket);

        model.addAttribute("errorMessage", errorMessage);
        return "ticket/payTicket";
    }
}

