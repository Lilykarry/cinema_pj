package com.example.demo.service.impl;

import com.example.demo.model.Showtimes;
import com.example.demo.model.Ticket;
import com.example.demo.repository.ShowTimeRepository;
import com.example.demo.repository.TicketRepository;
import com.example.demo.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Override
    public void create(Ticket newtk) {
        ticketRepository.save(newtk);
    }

    @Override
    public Ticket findByTicketID(int id) {
        return ticketRepository.findByTicketId(id);
    }

    @Override
    public List<Ticket> showAll() {
        return ticketRepository.findAll();
    }
    @Override
    public List<Ticket> showAllAdmin() {
        return ticketRepository.findAll(Sort.by(Sort.Direction.DESC, "ticketId"));
    }

    @Override
    public List<Ticket> showAllByEmal(String email) {
        return ticketRepository.findByUserEmail_Email(email);
    }

    @Override
    public void remove(int id) {
        ticketRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void deleteTicketByStatus(int status) {
        ticketRepository.deleteAllByStatus(status);
    }
}
