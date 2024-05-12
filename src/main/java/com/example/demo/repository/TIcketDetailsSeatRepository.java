package com.example.demo.repository;

import com.example.demo.model.Seat;
import com.example.demo.model.TicketDetailsSeat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TIcketDetailsSeatRepository extends JpaRepository<TicketDetailsSeat,Integer> {
    List<TicketDetailsSeat> findByTicketId_TicketId(int id);
}
