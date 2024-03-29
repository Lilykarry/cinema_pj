/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.demo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import java.util.Collection;

@Entity
@Table(name = "Language", catalog = "CinestarDatabase", schema = "dbo")

public class Language {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "languageId", nullable = false)
    private Integer languageId;
    @Size(max = 50)
    @Column(name = "languageName", length = 50)
    private String languageName;
    @OneToMany(mappedBy = "idLanguage")
    private Collection<MovieLanguage> movieLanguageCollection;

    public Language() {
    }

    public Language(Integer languageId) {
        this.languageId = languageId;
    }

    public Integer getLanguageId() {
        return languageId;
    }

    public void setLanguageId(Integer languageId) {
        this.languageId = languageId;
    }

    public String getLanguageName() {
        return languageName;
    }

    public void setLanguageName(String languageName) {
        this.languageName = languageName;
    }

    public Collection<MovieLanguage> getMovieLanguageCollection() {
        return movieLanguageCollection;
    }

    public void setMovieLanguageCollection(Collection<MovieLanguage> movieLanguageCollection) {
        this.movieLanguageCollection = movieLanguageCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (languageId != null ? languageId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Language)) {
            return false;
        }
        Language other = (Language) object;
        if ((this.languageId == null && other.languageId != null) || (this.languageId != null && !this.languageId.equals(other.languageId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "models.Language[ languageId=" + languageId + " ]";
    }
    
}
