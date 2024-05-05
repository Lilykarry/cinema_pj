package com.example.demo.service;

import com.example.demo.model.WaterCorn;

import java.util.List;

public interface WaterCornService {
    WaterCorn findById(int waterCornId);
    List<WaterCorn> listAll();
}
