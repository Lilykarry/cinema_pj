package com.example.demo.service.impl;

import com.example.demo.model.WaterCorn;
import com.example.demo.repository.WaterCornRepository;
import com.example.demo.service.WaterCornService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WaterCornServiceImpl implements WaterCornService {
    @Autowired
    private WaterCornRepository waterCornRepository;
    @Override
    public WaterCorn findById(int waterCornId) {
        return waterCornRepository.findWaterCornByIdWaterCorn(waterCornId);
    }

    @Override
    public List<WaterCorn> listAll() {
        return waterCornRepository.findAll();
    }
}
