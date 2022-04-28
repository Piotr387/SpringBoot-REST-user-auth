package com.pp.app.service.implementation;

import com.pp.app.io.entity.AddressEntity;
import com.pp.app.io.entity.UserEntity;
import com.pp.app.io.repositories.AddressRepository;
import com.pp.app.io.repositories.UserRepository;
import com.pp.app.service.AddressService;
import com.pp.app.ui.shared.DTO.AddressDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AddressServiceImplementation implements AddressService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    AddressRepository addressRepository;

    @Autowired
    AddressRepository addressesRepository;

    @Override
    public List<AddressDTO> getAddresses(String userId) {
        UserEntity userEntity = userRepository.findByUserId(userId).orElseThrow( () -> {
            throw new UsernameNotFoundException(userId);
        });

        List<AddressEntity> addressEntities = addressesRepository.findAllByUserDetails(userEntity).orElseThrow(() -> {
            throw new RuntimeException("No addresses found");
        });
        List<AddressDTO> returnValue = addressEntities.stream()
                .map( entity -> new ModelMapper().map(entity, AddressDTO.class))
                .collect(Collectors.toList());


        return returnValue;
    }



    @Override
    public AddressDTO getAddress(String addressId) {
        AddressEntity addressEntity = addressRepository.findByAddressId(addressId).orElseThrow(() -> {
            throw new UsernameNotFoundException(addressId);
        });
        return new ModelMapper().map(addressEntity, AddressDTO.class);
    }
}
