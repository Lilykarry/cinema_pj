package com.example.demo.repository;

import com.example.demo.model.Seat;
import com.example.demo.model.Showtimes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SeatRepository extends JpaRepository<Seat, Integer> {
    Seat findSeatBySeatId(int id);

}
