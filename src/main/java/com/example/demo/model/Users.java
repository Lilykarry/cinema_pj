
package com.example.demo.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.math.BigInteger;
import java.util.Collection;
import java.util.Date;

@Entity
@Table(name = "Users", catalog = "CinestarDatabase", schema = "dbo")

public class Users{
    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Invalid email")//if the field contains email address consider using this annotation to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 250)
    @Column(name = "email", nullable = false, length = 250)
    private String email;
    @Size(max = 100)
    @Column(name = "name", length = 100)
    private String name;
    @Column(name = "dob")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dob;
    // @Pattern(regexp="^\\(?(\\d{3})\\)?[- ]?(\\d{3})[- ]?(\\d{4})$", message="Invalid phone/fax format, should be as xxx-xxx-xxxx")//if the field contains phone or fax number consider using this annotation to enforce field validation
    @Size(max = 50)
    @Column(name = "phone", length = 50)
    private String phone;
    @Size(max = 500)
    @Column(name = "password", length = 500)
    private String password;
    @Size(max = 500)
    @Column(name = "address", length = 500)
    private String address;
    @Column(name = "tokenlifetime")
    private BigInteger tokenlifetime;
    @Size(max = 250)
    @Column(name = "token", length = 250)
    private String token;
    @Size(max = 100)
    @Column(name = "province", length = 100)
    private String province;
    @Size(max = 100)
    @Column(name = "district", length = 100)
    private String district;
    @Size(max = 50)
    @Column(name = "ward", length = 50)
    private String ward;
    @Size(max = 50)
    @Column(name = "Gender", length = 50)
    private String gender;
    @Size(max = 100)
    @Column(name = "id_facebook", length = 100)
    private String idFacebook;
    @Size(max = 100)
    @Column(name = "id_google", length = 100)
    private String idGoogle;
    @OneToMany(mappedBy = "userEmail")
    @JsonIgnore
    private Collection<Coupon> couponCollection;
    @OneToMany(mappedBy = "userEmail")
    @JsonIgnore
    private Collection<Ticket> ticketCollection;
    @OneToMany(mappedBy = "userEmail")
    @JsonIgnore
    private Collection<MembershipCard> membershipCardCollection;
    @OneToMany(mappedBy = "userEmail")
    @JsonIgnore
    private Collection<Wishlist> wishlistCollection;
    @OneToMany(mappedBy = "userEmail")
    @JsonIgnore
    private Collection<RatingThreater> ratingThreaterCollection;

    public Users(String email, String password, String fullName, Date dateOfBirth, String phoneNumber, String gender) {
    }

    public Collection<RatingThreater> getRatingThreaterCollection() {
        return ratingThreaterCollection;
    }

    public void setRatingThreaterCollection(Collection<RatingThreater> ratingThreaterCollection) {
        this.ratingThreaterCollection = ratingThreaterCollection;
    }

    public Users() {
    }

    public Users(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public BigInteger getTokenlifetime() {
        return tokenlifetime;
    }

    public void setTokenlifetime(BigInteger tokenlifetime) {
        this.tokenlifetime = tokenlifetime;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getWard() {
        return ward;
    }

    public void setWard(String ward) {
        this.ward = ward;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getIdFacebook() {
        return idFacebook;
    }

    public void setIdFacebook(String idFacebook) {
        this.idFacebook = idFacebook;
    }

    public String getIdGoogle() {
        return idGoogle;
    }

    public void setIdGoogle(String idGoogle) {
        this.idGoogle = idGoogle;
    }

    public Collection<Coupon> getCouponCollection() {
        return couponCollection;
    }

    public Users(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public Users(String email, String name, Date dob, String phone, String password, String address, String province, String district, String ward, String gender) {
        this.email = email;
        this.name = name;
        this.dob = dob;
        this.phone = phone;
        this.password = password;
        this.address = address;

        this.province = province;
        this.district = district;
        this.ward = ward;
        this.gender = gender;

    }

    public void setCouponCollection(Collection<Coupon> couponCollection) {
        this.couponCollection = couponCollection;
    }

    public Collection<Ticket> getTicketCollection() {
        return ticketCollection;
    }

    public void setTicketCollection(Collection<Ticket> ticketCollection) {
        this.ticketCollection = ticketCollection;
    }


    public Collection<MembershipCard> getMembershipCardCollection() {
        return membershipCardCollection;
    }

    public void setMembershipCardCollection(Collection<MembershipCard> membershipCardCollection) {
        this.membershipCardCollection = membershipCardCollection;
    }


    public Collection<Wishlist> getWishlistCollection() {
        return wishlistCollection;
    }

    public void setWishlistCollection(Collection<Wishlist> wishlistCollection) {
        this.wishlistCollection = wishlistCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (email != null ? email.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Users)) {
            return false;
        }
        Users other = (Users) object;
        if ((this.email == null && other.email != null) || (this.email != null && !this.email.equals(other.email))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "models.Users[ email=" + email + " ]";
    }

}
