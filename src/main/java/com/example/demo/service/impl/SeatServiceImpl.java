package com.example.demo.service.impl;

import com.example.demo.model.Seat;
import com.example.demo.repository.SeatRepository;
import com.example.demo.service.SeatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SeatServiceImpl implements SeatService {
    @Autowired
    private SeatRepository seatRepository;


    @Override
    public Seat findById(int i) {
        return seatRepository.findSeatBySeatId(i);
    }
}
