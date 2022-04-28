package com.pp.app.service;

import com.pp.app.ui.shared.DTO.AddressDTO;

import java.util.List;

public interface AddressService {
    List<AddressDTO> getAddresses(String userId);
    AddressDTO getAddress(String addressId);
}
