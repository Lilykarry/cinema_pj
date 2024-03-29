/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.demo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.Collection;

@Entity
@Table(name = "Room", catalog = "CinestarDatabase", schema = "dbo")

public class Room{

    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "roomId", nullable = false, length = 10)
    private String roomId;
    @Size(max = 50)
    @Column(name = "roomName", length = 50)
    private String roomName;
    @Column(name = "roomSize")
    private Integer roomSize;
    @Size(max = 50)
    @Column(name = "roomCategory", length = 50)
    private String roomCategory;
    @OneToMany(mappedBy = "roomId")
    private Collection<RowOfSeats> rowOfSeatsCollection;
    @OneToMany(mappedBy = "roomId")
    private Collection<Showtimes> showtimesCollection;
    @JoinColumn(name = "threatId", referencedColumnName = "id")
    @ManyToOne
    private Threat threatId;

    public Room() {
    }

    public Room(String roomId) {
        this.roomId = roomId;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public Integer getRoomSize() {
        return roomSize;
    }

    public void setRoomSize(Integer roomSize) {
        this.roomSize = roomSize;
    }

    public String getRoomCategory() {
        return roomCategory;
    }

    public void setRoomCategory(String roomCategory) {
        this.roomCategory = roomCategory;
    }

    public Collection<RowOfSeats> getRowOfSeatsCollection() {
        return rowOfSeatsCollection;
    }

    public void setRowOfSeatsCollection(Collection<RowOfSeats> rowOfSeatsCollection) {
        this.rowOfSeatsCollection = rowOfSeatsCollection;
    }

    public Collection<Showtimes> getShowtimesCollection() {
        return showtimesCollection;
    }

    public void setShowtimesCollection(Collection<Showtimes> showtimesCollection) {
        this.showtimesCollection = showtimesCollection;
    }

    public Threat getThreatId() {
        return threatId;
    }

    public void setThreatId(Threat threatId) {
        this.threatId = threatId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (roomId != null ? roomId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Room)) {
            return false;
        }
        Room other = (Room) object;
        if ((this.roomId == null && other.roomId != null) || (this.roomId != null && !this.roomId.equals(other.roomId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "models.Room[ roomId=" + roomId + " ]";
    }
    
}
