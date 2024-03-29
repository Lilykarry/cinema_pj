/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.demo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "MovieReview", catalog = "CinestarDatabase", schema = "dbo")

public class MovieReview{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    private Integer id;
    @Size(max = 100)
    @Column(name = "title", length = 100)
    private String title;
    @Size(max = 250)
    @Column(name = "titleR", length = 250)
    private String titleR;
    @Size(max = 2147483647)
    @Column(name = "movieReviewContent", length = 2147483647)
    private String movieReviewContent;
    @Column(name = "movieReviewView")
    private Integer movieReviewView;
    @Column(name = "type")
    private Boolean type;
    @Size(max = 500)
    @Column(name = "image", length = 500)
    private String image;

    public MovieReview() {
    }

    public MovieReview(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitleR() {
        return titleR;
    }

    public void setTitleR(String titleR) {
        this.titleR = titleR;
    }

    public String getMovieReviewContent() {
        return movieReviewContent;
    }

    public void setMovieReviewContent(String movieReviewContent) {
        this.movieReviewContent = movieReviewContent;
    }

    public Integer getMovieReviewView() {
        return movieReviewView;
    }

    public void setMovieReviewView(Integer movieReviewView) {
        this.movieReviewView = movieReviewView;
    }

    public Boolean getType() {
        return type;
    }

    public void setType(Boolean type) {
        this.type = type;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MovieReview)) {
            return false;
        }
        MovieReview other = (MovieReview) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "models.MovieReview[ id=" + id + " ]";
    }
    
}
