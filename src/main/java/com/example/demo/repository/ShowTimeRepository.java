package com.example.demo.repository;

import com.example.demo.model.Showtimes;
import com.example.demo.model.Threat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShowTimeRepository extends JpaRepository<Threat, Integer> {



    @Query("SELECT DISTINCT t FROM Showtimes s JOIN s.roomId r JOIN r.threatId t")
    List<Threat> findAllThreats();
}
