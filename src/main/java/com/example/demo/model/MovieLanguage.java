/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.demo.model;

import jakarta.persistence.*;

@Entity
@Table(name = "MovieLanguage", catalog = "CinestarDatabase", schema = "dbo")

public class MovieLanguage{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idMovieLanguage", nullable = false)
    private Integer idMovieLanguage;
    @JoinColumn(name = "idLanguage", referencedColumnName = "languageId")
    @ManyToOne
    private Language idLanguage;
    @JoinColumn(name = "idMovie", referencedColumnName = "movieId")
    @ManyToOne
    private Movie idMovie;

    public MovieLanguage() {
    }

    public MovieLanguage(Integer idMovieLanguage) {
        this.idMovieLanguage = idMovieLanguage;
    }

    public Integer getIdMovieLanguage() {
        return idMovieLanguage;
    }

    public void setIdMovieLanguage(Integer idMovieLanguage) {
        this.idMovieLanguage = idMovieLanguage;
    }

    public Language getIdLanguage() {
        return idLanguage;
    }

    public void setIdLanguage(Language idLanguage) {
        this.idLanguage = idLanguage;
    }

    public Movie getIdMovie() {
        return idMovie;
    }

    public void setIdMovie(Movie idMovie) {
        this.idMovie = idMovie;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idMovieLanguage != null ? idMovieLanguage.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MovieLanguage)) {
            return false;
        }
        MovieLanguage other = (MovieLanguage) object;
        if ((this.idMovieLanguage == null && other.idMovieLanguage != null) || (this.idMovieLanguage != null && !this.idMovieLanguage.equals(other.idMovieLanguage))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "models.MovieLanguage[ idMovieLanguage=" + idMovieLanguage + " ]";
    }
    
}
