package com.example.demo.repository;

import com.example.demo.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieRepository extends JpaRepository<Movie, String> {
    @Query("SELECT m FROM Movie m WHERE m.movieTitle LIKE %?1%")
    List<Movie> findByTitleContaining(String keyword);
    List<Movie> findByMovieTitle(String search);
    Movie findByMovieId(String id);



}
