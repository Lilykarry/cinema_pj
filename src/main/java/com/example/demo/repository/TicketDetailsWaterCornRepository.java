package com.example.demo.repository;

import com.example.demo.model.TicketDetailsWaterCorn;
import com.example.demo.model.WaterCorn;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TicketDetailsWaterCornRepository extends JpaRepository<TicketDetailsWaterCorn,Integer> {

    TicketDetailsWaterCorn findByIdWaterCorn_IdWaterCorn(int id);
    List<TicketDetailsWaterCorn> findByTicketId_TicketId(int id);
}
