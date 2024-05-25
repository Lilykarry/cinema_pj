package com.example.demo.service;

import com.example.demo.model.Seat;
import com.example.demo.model.TicketDetailsSeat;

import java.util.List;

public interface TicketDetailsSeatService {
    void create(TicketDetailsSeat newtk);
    List<TicketDetailsSeat> searchSeatsByTicketId(int id);
    void deleteSeatsByTicketId(int id);
}
