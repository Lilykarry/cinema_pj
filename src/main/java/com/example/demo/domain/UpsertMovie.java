package com.example.demo.domain;

import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;


public class UpsertMovie {
    private String movieId;

    private String movieTitle;

    private String movieCategory;

    private String performer;

    private String filmDirector;

    private String movieContent;

    private MultipartFile image;

    private String trailer;

    private LocalDate startMovie;

    private LocalDate endMovie;

    private Integer status;

    public String getMovieId() {
        return movieId;
    }

    public void setMovieId(String movieId) {
        this.movieId = movieId;
    }

    public String getMovieTitle() {
        return movieTitle;
    }

    public void setMovieTitle(String movieTitle) {
        this.movieTitle = movieTitle;
    }

    public String getMovieCategory() {
        return movieCategory;
    }

    public void setMovieCategory(String movieCategory) {
        this.movieCategory = movieCategory;
    }

    public String getPerformer() {
        return performer;
    }

    public void setPerformer(String performer) {
        this.performer = performer;
    }

    public String getFilmDirector() {
        return filmDirector;
    }

    public void setFilmDirector(String filmDirector) {
        this.filmDirector = filmDirector;
    }

    public String getMovieContent() {
        return movieContent;
    }

    public void setMovieContent(String movieContent) {
        this.movieContent = movieContent;
    }

    public MultipartFile getImage() {
        return image;
    }

    public void setImage(MultipartFile image) {
        this.image = image;
    }

    public String getTrailer() {
        return trailer;
    }

    public void setTrailer(String trailer) {
        this.trailer = trailer;
    }

    public LocalDate getStartMovie() {
        return startMovie;
    }

    public void setStartMovie(LocalDate startMovie) {
        this.startMovie = startMovie;
    }

    public LocalDate getEndMovie() {
        return endMovie;
    }

    public void setEndMovie(LocalDate endMovie) {
        this.endMovie = endMovie;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
