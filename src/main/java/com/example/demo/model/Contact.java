/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.demo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;


@Entity
@Table(name = "Contact", catalog = "CinestarDatabase", schema = "dbo")

public class Contact {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "Cid", nullable = false)
    private Integer cid;
    @Size(max = 50)
    @Column(name = "Cname", length = 50)
    private String cname;
    @Size(max = 100)
    @Column(name = "Cemail", length = 100)
    private String cemail;
    @Size(max = 11)
    @Column(name = "Cphone", length = 11)
    private String cphone;
    @Size(max = 2147483647)
    @Column(name = "Ccontent", length = 2147483647)
    private String ccontent;

    public Contact() {
    }

    public Contact(Integer cid) {
        this.cid = cid;
    }

    public Integer getCid() {
        return cid;
    }

    public void setCid(Integer cid) {
        this.cid = cid;
    }

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public String getCemail() {
        return cemail;
    }

    public void setCemail(String cemail) {
        this.cemail = cemail;
    }

    public String getCphone() {
        return cphone;
    }

    public void setCphone(String cphone) {
        this.cphone = cphone;
    }

    public String getCcontent() {
        return ccontent;
    }

    public void setCcontent(String ccontent) {
        this.ccontent = ccontent;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (cid != null ? cid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Contact)) {
            return false;
        }
        Contact other = (Contact) object;
        if ((this.cid == null && other.cid != null) || (this.cid != null && !this.cid.equals(other.cid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "models.Contact[ cid=" + cid + " ]";
    }
    
}
