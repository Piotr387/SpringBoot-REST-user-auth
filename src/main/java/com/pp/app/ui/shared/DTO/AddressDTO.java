package com.pp.app.ui.shared.DTO;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AddressDTO {
    private long id;
    private String addressId;
    private String city;
    private String country;
    private String streetName;
    private String postalCode;
    private String type;
    private UserDto userDto;

}
