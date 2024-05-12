/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.demo.model;

import jakarta.persistence.*;

@Entity
@Table(name = "TicketDetailsWaterCorn", catalog = "CinestarDatabase", schema = "dbo")
public class TicketDetailsWaterCorn {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ticketDetailsWaterCornId", nullable = false)
    private Integer ticketDetailsWaterCornId;
    @Column(name = "soLuong")
    private Integer soLuong;
    @Column(name = "unitPrice")
    private Integer unitPrice;
    @JoinColumn(name = "ticketId", referencedColumnName = "ticketId")
    @ManyToOne
    private Ticket ticketId;
    @JoinColumn(name = "idWaterCorn", referencedColumnName = "idWaterCorn")
    @ManyToOne
    private WaterCorn idWaterCorn;

    public TicketDetailsWaterCorn() {
    }

    public TicketDetailsWaterCorn(Integer ticketDetailsWaterCornId) {
        this.ticketDetailsWaterCornId = ticketDetailsWaterCornId;
    }

    public Integer getTicketDetailsWaterCornId() {
        return ticketDetailsWaterCornId;
    }

    public void setTicketDetailsWaterCornId(Integer ticketDetailsWaterCornId) {
        this.ticketDetailsWaterCornId = ticketDetailsWaterCornId;
    }

    public Integer getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(Integer soLuong) {
        this.soLuong = soLuong;
    }

    public Integer getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Integer unitPrice) {
        this.unitPrice = unitPrice;
    }

    public Ticket getTicketId() {
        return ticketId;
    }

    public void setTicketId(Ticket ticketId) {

        this.ticketId = ticketId;
        if (ticketId != null) {
            ticketId.getTicketDetailsWaterCornCollection().add(this);
        }
    }

    public WaterCorn getIdWaterCorn() {
        return idWaterCorn;
    }

    public void setIdWaterCorn(WaterCorn idWaterCorn) {
        this.idWaterCorn = idWaterCorn;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (ticketDetailsWaterCornId != null ? ticketDetailsWaterCornId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TicketDetailsWaterCorn)) {
            return false;
        }
        TicketDetailsWaterCorn other = (TicketDetailsWaterCorn) object;
        if ((this.ticketDetailsWaterCornId == null && other.ticketDetailsWaterCornId != null) || (this.ticketDetailsWaterCornId != null && !this.ticketDetailsWaterCornId.equals(other.ticketDetailsWaterCornId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "models.TicketDetailsWaterCorn[ ticketDetailsWaterCornId=" + ticketDetailsWaterCornId + " ]";
    }
    
}
