package com.example.demo.service.impl;

import com.example.demo.model.Endow;
import com.example.demo.repository.EndowRepository;
import com.example.demo.service.EndowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EndowServiceImpl implements EndowService {

    @Autowired
    private EndowRepository endowRepository;


    @Override
    public List<Endow> showAllPromotions() {
        return endowRepository.findAll();
    }
}
