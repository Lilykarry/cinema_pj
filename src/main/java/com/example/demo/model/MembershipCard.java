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
@Table(name = "MembershipCard", catalog = "CinestarDatabase", schema = "dbo")

public class MembershipCard{
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "cardno", nullable = false, length = 50)
    private String cardno;
    @Basic(optional = false)
    @NotNull
    @Column(name = "totalspend", nullable = false)
    private int totalspend;
    @Column(name = "dateregister")
    @Temporal(TemporalType.DATE)
    private Date dateregister;
    @Column(name = "Point")
    private Integer point;
    @JoinColumn(name = "user_email", referencedColumnName = "email")
    @ManyToOne
    private Users userEmail;

    public MembershipCard() {
    }

    public MembershipCard(String cardno) {
        this.cardno = cardno;
    }

    public MembershipCard(String cardno, int totalspend) {
        this.cardno = cardno;
        this.totalspend = totalspend;
    }

    public String getCardno() {
        return cardno;
    }

    public void setCardno(String cardno) {
        this.cardno = cardno;
    }

    public int getTotalspend() {
        return totalspend;
    }

    public void setTotalspend(int totalspend) {
        this.totalspend = totalspend;
    }

    public Date getDateregister() {
        return dateregister;
    }

    public void setDateregister(Date dateregister) {
        this.dateregister = dateregister;
    }

    public Integer getPoint() {
        return point;
    }

    public void setPoint(Integer point) {
        this.point = point;
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
        hash += (cardno != null ? cardno.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MembershipCard)) {
            return false;
        }
        MembershipCard other = (MembershipCard) object;
        if ((this.cardno == null && other.cardno != null) || (this.cardno != null && !this.cardno.equals(other.cardno))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "models.MembershipCard[ cardno=" + cardno + " ]";
    }
    
}
