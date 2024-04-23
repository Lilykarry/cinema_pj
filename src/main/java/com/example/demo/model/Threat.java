/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.demo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import java.util.Collection;
import java.util.stream.Collectors;


@Entity
@Table(name = "Threat")

public class Threat{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Size(max = 200)
    @Column(name = "name")
    private String name;
    // @Pattern(regexp="^\\(?(\\d{3})\\)?[- ]?(\\d{3})[- ]?(\\d{4})$", message="Invalid phone/fax format, should be as xxx-xxx-xxxx")//if the field contains phone or fax number consider using this annotation to enforce field validation
    @Size(max = 50)
    @Column(name = "phone")
    private String phone;
    @Size(max = 500)
    @Column(name = "address")
    private String address;
    @Size(max = 5000)
    @Column(name = "img")
    private String img;

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    @Size(max = 50)
    @Column(name = "region")
    private String region;
    @OneToMany(mappedBy = "threatId")
    private Collection<RatingThreater> ratingThreaterCollection;
    @OneToMany(mappedBy = "threatId")
    private Collection<Room> rooms;


    public Threat() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public Collection<RatingThreater> getRatingThreaterCollection() {
        return ratingThreaterCollection;
    }

    public void setRatingThreaterCollection(Collection<RatingThreater> ratingThreaterCollection) {
        this.ratingThreaterCollection = ratingThreaterCollection;
    }

    public Collection<Room> getRooms() {
        return rooms;
    }

    public void setRooms(Collection<Room> rooms) {
        this.rooms = rooms;
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
        if (!(object instanceof Threat)) {
            return false;
        }
        Threat other = (Threat) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }
    public String getRoomIdsAsString() {
        if (rooms == null || rooms.isEmpty()) {
            return "";
        }
        return rooms.stream()
                .map(room -> room.getRoomId())
                .collect(Collectors.joining(", "));
    }
    @Override
    public String toString() {
        return "models.Threat[ id=" + id + " ]";
    }

}
