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
@Table(name = "WaterCorn", catalog = "CinestarDatabase", schema = "dbo")

public class WaterCorn {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idWaterCorn", nullable = false)
    private Integer idWaterCorn;
    @Size(max = 50)
    @Column(name = "nameWaterCorn", length = 50)
    private String nameWaterCorn;
    @Size(max = 500)
    @Column(name = "contentWaterCorn", length = 500)
    private String contentWaterCorn;
    @Size(max = 250)
    @Column(name = "imageWaterCorn", length = 250)
    private String imageWaterCorn;
    @Column(name = "price")
    private Integer price;
    @OneToMany(mappedBy = "idWaterCorn")
    private Collection<TicketDetailsWaterCorn> ticketDetailsWaterCornCollection;

    public WaterCorn() {
    }

    public WaterCorn(Integer idWaterCorn) {
        this.idWaterCorn = idWaterCorn;
    }

    public Integer getIdWaterCorn() {
        return idWaterCorn;
    }

    public void setIdWaterCorn(Integer idWaterCorn) {
        this.idWaterCorn = idWaterCorn;
    }

    public String getNameWaterCorn() {
        return nameWaterCorn;
    }

    public void setNameWaterCorn(String nameWaterCorn) {
        this.nameWaterCorn = nameWaterCorn;
    }

    public String getContentWaterCorn() {
        return contentWaterCorn;
    }

    public void setContentWaterCorn(String contentWaterCorn) {
        this.contentWaterCorn = contentWaterCorn;
    }

    public String getImageWaterCorn() {
        return imageWaterCorn;
    }

    public void setImageWaterCorn(String imageWaterCorn) {
        this.imageWaterCorn = imageWaterCorn;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
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
        hash += (idWaterCorn != null ? idWaterCorn.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof WaterCorn)) {
            return false;
        }
        WaterCorn other = (WaterCorn) object;
        if ((this.idWaterCorn == null && other.idWaterCorn != null) || (this.idWaterCorn != null && !this.idWaterCorn.equals(other.idWaterCorn))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "models.WaterCorn[ idWaterCorn=" + idWaterCorn + " ]";
    }
    
}
