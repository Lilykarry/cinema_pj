package com.example.demo.service;

import com.example.demo.model.Showtimes;
import com.example.demo.model.Ticket;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface TicketService {
    Showtimes findShowtimesByDateTimeMvID(LocalDate day, LocalTime time, String movieID);
    List<Ticket> findAllTicketsByShowtimesByStatus(int id, int status);
    Showtimes findByID(int id);
    void create(Ticket newtk);
    Ticket findByTicketID(int id);
    List<Ticket> showAll();

    List<Ticket> showAllAdmin();

    List<Ticket> showAllByEmal(String email);
    void remove(int id);
    void deleteTicketByStatus(int status);
}
