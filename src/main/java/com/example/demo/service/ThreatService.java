package com.example.demo.service;

import com.example.demo.domain.UpsertThreat;
import com.example.demo.exception.MovieNotFoundExeption;
import com.example.demo.model.Threat;

import java.io.IOException;
import java.util.List;

public interface ThreatService {
    List<Threat> showAllTheater();


    void save(UpsertThreat threat) throws IOException, MovieNotFoundExeption;

    Threat get(Integer Id) throws MovieNotFoundExeption;

    void deleteThreatById(Integer id);
}
