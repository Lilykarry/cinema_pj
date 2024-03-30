package com.example.demo.service.impl;

import com.example.demo.model.Threat;
import com.example.demo.repository.ThreatRepository;
import com.example.demo.service.ThreatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TheatServiceImpl implements ThreatService {

    @Autowired
    private ThreatRepository theatRepository;

    @Override
    public List<Threat> showAllTheater() {
        return theatRepository.findAll();
    }
}
