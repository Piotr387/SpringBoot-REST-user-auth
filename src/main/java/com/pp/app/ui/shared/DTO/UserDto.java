package com.pp.app.ui.shared.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * DTO - DATA TRANSFER OBJECT
 */
@Data
@NoArgsConstructor
public class UserDto implements Serializable {


    @Serial
    private static final long serialVersionUID = -3519757051765466055L;
    private long id;
    private String userId;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String encryptedPassword;
    private String emailVerificationToken;
    private Boolean emailVerificationStatus = false;
    private List<AddressDTO> addresses;
}
