package com.example.demo.service.impl;

import com.example.demo.model.MovieReview;
import com.example.demo.repository.MovieReviewRepository;
import com.example.demo.service.MovieReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovieReviewServiceImpl implements MovieReviewService {

    @Autowired
    private MovieReviewRepository movieReviewRepository;

    @Override
    public List<MovieReview> showAllNews() {
        return movieReviewRepository.findAll();
    }

    @Override
    public List<MovieReview> getAllDESC() {
        return movieReviewRepository.findAll();
    }

    @Override
    public MovieReview findRvById(Integer id) {
        return movieReviewRepository.findMovieReviewById(id);
    }
}
