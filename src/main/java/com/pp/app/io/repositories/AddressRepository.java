package com.pp.app.io.repositories;

import com.pp.app.io.entity.AddressEntity;
import com.pp.app.io.entity.UserEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface AddressRepository extends CrudRepository<AddressEntity, Long> {
    Optional<List<AddressEntity>> findAllByUserDetails(UserEntity userEntity);
    Optional<AddressEntity> findByAddressId(String addressId);
}
