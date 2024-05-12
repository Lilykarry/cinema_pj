package com.example.demo.service.impl;

import com.example.demo.model.TicketDetailsWaterCorn;
import com.example.demo.repository.TicketDetailsWaterCornRepository;
import com.example.demo.service.TicketDetailsWaterCornService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TicketDetailsWaterCornServiceImpl implements TicketDetailsWaterCornService {

    @Autowired
    private TicketDetailsWaterCornRepository ticketDetailsWaterCornRepository;

    @Override
    public void create(TicketDetailsWaterCorn newTk) {
        ticketDetailsWaterCornRepository.save(newTk);
    }

    @Override
    public List<TicketDetailsWaterCorn> searchWaterCornssByTicketId(int id) {
        return ticketDetailsWaterCornRepository.findByTicketId_TicketId(id);
    }


}
