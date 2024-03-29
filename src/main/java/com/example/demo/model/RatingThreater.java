/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.demo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.Date;

@Entity
@Table(name = "RatingThreater")
public class RatingThreater{

    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id")
    private Integer id;
    @Column(name = "numberrating")
    private Integer numberrating;
    @Size(max = 250)
    @Column(name = "comment")
    private String comment;
    @Column(name = "status")
    private Integer status;
    @Column(name = "datecomment")
    @Temporal(TemporalType.DATE)
    private Date datecomment;
    @JoinColumn(name = "threat_id", referencedColumnName = "id")
    @ManyToOne
    private Threat threatId;
    @JoinColumn(name = "user_email", referencedColumnName = "email")
    @ManyToOne
    private Users userEmail;

    public RatingThreater() {
    }

    public RatingThreater(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getNumberrating() {
        return numberrating;
    }

    public void setNumberrating(Integer numberrating) {
        this.numberrating = numberrating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getDatecomment() {
        return datecomment;
    }

    public void setDatecomment(Date datecomment) {
        this.datecomment = datecomment;
    }

    public Threat getThreatId() {
        return threatId;
    }

    public void setThreatId(Threat threatId) {
        this.threatId = threatId;
    }

    public Users getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(Users userEmail) {
        this.userEmail = userEmail;
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
        if (!(object instanceof RatingThreater)) {
            return false;
        }
        RatingThreater other = (RatingThreater) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "models.RatingThreater[ id=" + id + " ]";
    }
    
}
