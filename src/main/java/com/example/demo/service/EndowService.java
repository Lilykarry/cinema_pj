package com.example.demo.service;

import com.example.demo.domain.UpsertEndow;
import com.example.demo.exception.MovieNotFoundExeption;
import com.example.demo.model.Endow;

import java.io.IOException;
import java.util.List;

public interface EndowService {
    List<Endow> showAllPromotions();

   

    List<Endow> searchEndowByTitle(String keyword);

    void deleteEndowById(String endowId);

    void save(UpsertEndow endow) throws IOException;

    Endow get(String endowId) throws MovieNotFoundExeption;
}
