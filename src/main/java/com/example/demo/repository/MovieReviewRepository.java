package com.example.demo.repository;

import com.example.demo.model.MovieReview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieReviewRepository  extends JpaRepository<MovieReview, Integer> {
}
