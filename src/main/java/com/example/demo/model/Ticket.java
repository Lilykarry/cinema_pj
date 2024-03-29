/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.demo.model;

import jakarta.persistence.*;

import java.util.Collection;
import java.util.Date;


@Entity
@Table(name = "Ticket", catalog = "CinestarDatabase", schema = "dbo")

public class Ticket{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ticketId", nullable = false)
    private Integer ticketId;
    @Column(name = "ticketBookingTime")
    @Temporal(TemporalType.DATE)
    private Date ticketBookingTime;
    @Column(name = "pay")
    private Integer pay;
    @Column(name = "totalPrice")
    private Integer totalPrice;
    @Column(name = "priceHasDropped")
    private Integer priceHasDropped;
    @Column(name = "status")
    private Integer status;
    @JoinColumn(name = "showtimeId", referencedColumnName = "showtimesId")
    @ManyToOne
    private Showtimes showtimeId;
    @JoinColumn(name = "user_email", referencedColumnName = "email")
    @ManyToOne
    private Users userEmail;
    @OneToMany(mappedBy = "ticketId", cascade = CascadeType.ALL, orphanRemoval = true)
    private Collection<TicketDetailsSeat> ticketDetailsSeatCollection;
    @OneToMany(mappedBy = "ticketId", cascade = CascadeType.ALL, orphanRemoval = true)
    private Collection<TicketDetailsWaterCorn> ticketDetailsWaterCornCollection;

    public Ticket() {
    }

    public Ticket(Integer ticketId) {
        this.ticketId = ticketId;
    }

    public Integer getTicketId() {
        return ticketId;
    }

    public void setTicketId(Integer ticketId) {
        this.ticketId = ticketId;
    }

    public Date getTicketBookingTime() {
        return ticketBookingTime;
    }

    public void setTicketBookingTime(Date ticketBookingTime) {
        this.ticketBookingTime = ticketBookingTime;
    }

    public Integer getPay() {
        return pay;
    }

    public void setPay(Integer pay) {
        this.pay = pay;
    }

    public Integer getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Integer totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Integer getPriceHasDropped() {
        return priceHasDropped;
    }

    public void setPriceHasDropped(Integer priceHasDropped) {
        this.priceHasDropped = priceHasDropped;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Showtimes getShowtimeId() {
        return showtimeId;
    }

    public void setShowtimeId(Showtimes showtimeId) {
        this.showtimeId = showtimeId;
    }

    public Users getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(Users userEmail) {
        this.userEmail = userEmail;
    }

    public Collection<TicketDetailsSeat> getTicketDetailsSeatCollection() {
        return ticketDetailsSeatCollection;
    }

    public void setTicketDetailsSeatCollection(Collection<TicketDetailsSeat> ticketDetailsSeatCollection) {
        this.ticketDetailsSeatCollection = ticketDetailsSeatCollection;
    }

    public Collection<TicketDetailsWaterCorn> getTicketDetailsWaterCornCollection() {
        return ticketDetailsWaterCornCollection;
    }

    public void setTicketDetailsWaterCornCollection(Collection<TicketDetailsWaterCorn> ticketDetailsWaterCornCollection) {
        this.ticketDetailsWaterCornCollection = ticketDetailsWaterCornCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (ticketId != null ? ticketId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Ticket)) {
            return false;
        }
        Ticket other = (Ticket) object;
        if ((this.ticketId == null && other.ticketId != null) || (this.ticketId != null && !this.ticketId.equals(other.ticketId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "models.Ticket[ ticketId=" + ticketId + " ]";
    }
    
}
