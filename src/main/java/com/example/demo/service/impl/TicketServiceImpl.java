package com.example.demo.service.impl;

import com.example.demo.model.Showtimes;
import com.example.demo.model.Ticket;
import com.example.demo.repository.ShowTimeRepository;
import com.example.demo.repository.TicketRepository;
import com.example.demo.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
public class TicketServiceImpl implements TicketService {

    @Autowired
    private ShowTimeRepository showTimeRepository;

    @Autowired
    private TicketRepository ticketRepository;

    @Override
    public Showtimes findShowtimesByDateTimeMvID(LocalDate day, LocalTime time, String movieID) {
        return showTimeRepository.findShowtimesByDateAndTimeAndMovieId_MovieId(day,time,movieID);
    }

    @Override
    public List<Ticket> findAllTicketsByShowtimesByStatus(int id, int status) {
        return ticketRepository.findByShowtimeId_ShowtimesIdAndStatus(id,status);
    }

    @Override
    public Showtimes findByID(int id) {
        return showTimeRepository.findShowtimesByShowtimesId(id);
    }
}
