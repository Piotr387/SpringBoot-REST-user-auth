package com.pp.app.io.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Data
@NoArgsConstructor
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

}
