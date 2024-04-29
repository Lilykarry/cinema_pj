package com.example.demo.repository;

import com.example.demo.model.WaterCorn;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WaterCornRepository extends JpaRepository<WaterCorn,Integer> {
    WaterCorn findWaterCornByIdWaterCorn (int waterCornId);
}
