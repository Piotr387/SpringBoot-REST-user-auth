package com.pp.app.io.entity;

import com.pp.app.ui.shared.DTO.UserDto;

import javax.persistence.*;
import java.io.Serializable;

@Entity(name = "addresses")
public class AddressEntity implements Serializable {

    private static final long serialVersionUID = -8864896755346662209L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column(name = "address_id", length = 30, nullable = false)
    private String addressId;
    @Column(length = 15, nullable = false)
    private String city;
    @Column(length = 15, nullable = false)
    private String country;
    @Column(name = "street_name", length = 100, nullable = false)
    private String streetName;
    @Column(name = "postal_code", length = 6, nullable = false)
    private String postalCode;
    @Column(length = 10, nullable = false)
    private String type;
    @ManyToOne
    @JoinColumn(name = "users_id")
    private UserEntity userDetails;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getAddressId() {
        return addressId;
    }

    public void setAddressId(String addressId) {
        this.addressId = addressId;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getStreetName() {
        return streetName;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public UserEntity getUserDetails() {
        return userDetails;
    }

    public void setUserDetails(UserEntity userDetails) {
        this.userDetails = userDetails;
    }
}
