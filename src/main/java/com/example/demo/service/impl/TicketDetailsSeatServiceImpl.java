package com.example.demo.service.impl;

import com.example.demo.model.Seat;
import com.example.demo.model.TicketDetailsSeat;
import com.example.demo.repository.SeatRepository;
import com.example.demo.repository.TIcketDetailsSeatRepository;
import com.example.demo.repository.TicketRepository;
import com.example.demo.service.TicketDetailsSeatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TicketDetailsSeatServiceImpl implements TicketDetailsSeatService {

    @Autowired
    private TIcketDetailsSeatRepository tIcketDetailsSeatRepository;

    @Autowired
    private SeatRepository seatRepository;

    @Override
    public void create(TicketDetailsSeat newtk) {
        tIcketDetailsSeatRepository.save(newtk);
    }

    @Override
    public List<TicketDetailsSeat> searchSeatsByTicketId(int id) {
        return tIcketDetailsSeatRepository.findByTicketId_TicketId(id);
    }

    @Override
    @Transactional
    public void deleteSeatsByTicketId(int id) {
        tIcketDetailsSeatRepository.deleteAllByTicketId_TicketId(id);
    }
}
