/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.demo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "Endow", catalog = "CinestarDatabase", schema = "dbo")

public class Endow {
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "endowId", nullable = false, length = 10)
    private String endowId;
    @Size(max = 2000)
    @Column(name = "endowContent", length = 2000)
    private String endowContent;
    @Size(max = 250)
    @Column(name = "endowImage", length = 250)
    private String endowImage;
    @Size(max = 50)
    @Column(name = "endowTitle", length = 50)
    private String endowTitle;

    public Endow() {
    }

    public Endow(String endowId) {
        this.endowId = endowId;
    }

    public String getEndowId() {
        return endowId;
    }

    public void setEndowId(String endowId) {
        this.endowId = endowId;
    }

    public String getEndowContent() {
        return endowContent;
    }

    public void setEndowContent(String endowContent) {
        this.endowContent = endowContent;
    }

    public String getEndowImage() {
        return endowImage;
    }

    public void setEndowImage(String endowImage) {
        this.endowImage = endowImage;
    }

    public String getEndowTitle() {
        return endowTitle;
    }

    public void setEndowTitle(String endowTitle) {
        this.endowTitle = endowTitle;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (endowId != null ? endowId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Endow)) {
            return false;
        }
        Endow other = (Endow) object;
        if ((this.endowId == null && other.endowId != null) || (this.endowId != null && !this.endowId.equals(other.endowId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "models.Endow[ endowId=" + endowId + " ]";
    }
    
}
