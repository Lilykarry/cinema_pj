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
@Table(name = "RowOfSeats", catalog = "CinestarDatabase", schema = "dbo")

public class RowOfSeats {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "rowId", nullable = false)
    private Integer rowId;
    @Size(max = 10)
    @Column(name = "rowNo", length = 10)
    private String rowNo;
    @OneToMany(mappedBy = "rowId")
    private Collection<Seat> seatCollection;
    @JoinColumn(name = "roomId", referencedColumnName = "roomId")
    @ManyToOne
    private Room roomId;

    public RowOfSeats() {
    }

    public RowOfSeats(Integer rowId) {
        this.rowId = rowId;
    }

    public Integer getRowId() {
        return rowId;
    }

    public void setRowId(Integer rowId) {
        this.rowId = rowId;
    }

    public String getRowNo() {
        return rowNo;
    }

    public void setRowNo(String rowNo) {
        this.rowNo = rowNo;
    }

    public Collection<Seat> getSeatCollection() {
        return seatCollection;
    }

    public void setSeatCollection(Collection<Seat> seatCollection) {
        this.seatCollection = seatCollection;
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
        hash += (rowId != null ? rowId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RowOfSeats)) {
            return false;
        }
        RowOfSeats other = (RowOfSeats) object;
        if ((this.rowId == null && other.rowId != null) || (this.rowId != null && !this.rowId.equals(other.rowId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "models.RowOfSeats[ rowId=" + rowId + " ]";
    }
    
}
