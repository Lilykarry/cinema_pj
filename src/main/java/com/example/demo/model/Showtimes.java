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
@Table(name = "Showtimes", catalog = "CinestarDatabase", schema = "dbo")

public class Showtimes{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "showtimesId", nullable = false)
    private Integer showtimesId;
    @Column(name = "time")
    @Temporal(TemporalType.TIME)
    private Date time;
    @Column(name = "date")
    @Temporal(TemporalType.DATE)
    private Date date;
    @OneToMany(mappedBy = "showtimeId")
    private Collection<Ticket> ticketCollection;
    @JoinColumn(name = "movieId", referencedColumnName = "movieId")
    @ManyToOne
    private Movie movieId;
    @JoinColumn(name = "roomId", referencedColumnName = "roomId")
    @ManyToOne
    private Room roomId;

    public Showtimes() {
    }

    public Showtimes(Integer showtimesId) {
        this.showtimesId = showtimesId;
    }

    public Integer getShowtimesId() {
        return showtimesId;
    }

    public void setShowtimesId(Integer showtimesId) {
        this.showtimesId = showtimesId;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Collection<Ticket> getTicketCollection() {
        return ticketCollection;
    }

    public void setTicketCollection(Collection<Ticket> ticketCollection) {
        this.ticketCollection = ticketCollection;
    }

    public Movie getMovieId() {
        return movieId;
    }

    public void setMovieId(Movie movieId) {
        this.movieId = movieId;
    }

    public Room getRoomId() {
        return roomId;
    }

    public void setRoomId(Room roomId) {
        this.roomId = roomId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (showtimesId != null ? showtimesId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Showtimes)) {
            return false;
        }
        Showtimes other = (Showtimes) object;
        if ((this.showtimesId == null && other.showtimesId != null) || (this.showtimesId != null && !this.showtimesId.equals(other.showtimesId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "models.Showtimes[ showtimesId=" + showtimesId + " ]";
    }
    public int calculateNumberTotalSeat() {
        int numberTotalSeat = 0;
        for (Ticket ticket : getTicketCollection()) {
            numberTotalSeat += ticket.getTicketDetailsSeatCollection().size();
        }
        return numberTotalSeat;
    }

    public int calculateTongSoGheTrongPhong() {
        int tongSoGheTrongPhong = 0;
        for (RowOfSeats ros : getRoomId().getRowOfSeatsCollection()) {
            tongSoGheTrongPhong += ros.getSeatCollection().size();
        }
        return tongSoGheTrongPhong;
    }
}
