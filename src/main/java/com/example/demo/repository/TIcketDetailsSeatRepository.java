package com.example.demo.repository;

import com.example.demo.model.Seat;
import com.example.demo.model.TicketDetailsSeat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Repository
public interface TIcketDetailsSeatRepository extends JpaRepository<TicketDetailsSeat,Integer> {
    List<TicketDetailsSeat> findByTicketId_TicketId(int id);
    @Transactional
    void deleteAllByTicketId_TicketId(int id);
}
