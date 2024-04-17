package com.example.demo.repository;

import com.example.demo.model.Threat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ThreatRepository extends JpaRepository<Threat,Integer> {


        @Query("SELECT MAX(t.id) FROM Threat t")
        Integer findMaxId();
}
