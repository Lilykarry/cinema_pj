package com.example.demo.repository;

import com.example.demo.model.Seat;
import com.example.demo.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface TicketRepository extends JpaRepository<Ticket,Integer> {
    List<Ticket> findByShowtimeId_ShowtimesIdAndStatus(int id,int status);
    Ticket findByTicketId(int id);
    List<Ticket> findByUserEmail_Email(String email);
    @Transactional
    void deleteAllByStatus(int status);
}
