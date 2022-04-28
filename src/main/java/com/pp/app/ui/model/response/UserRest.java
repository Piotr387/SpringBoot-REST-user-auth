package com.pp.app.ui.model.response;

import com.pp.app.ui.shared.DTO.AddressDTO;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class UserRest {

    private String userId;
    private String firstName;
    private String lastName;
    private String email;
    private List<AddressDTO> addresses;

}
