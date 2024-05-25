package com.example.demo.service;

import com.example.demo.model.TicketDetailsWaterCorn;

import java.util.List;

public interface TicketDetailsWaterCornService {
    void create(TicketDetailsWaterCorn newTk);
    List<TicketDetailsWaterCorn> searchWaterCornssByTicketId(int id);
    void deleteWaterCornsByTicketId(int id);
}
