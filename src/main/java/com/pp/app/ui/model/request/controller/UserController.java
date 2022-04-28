package com.pp.app.ui.model.request.controller;

import com.pp.app.service.AddressService;
import com.pp.app.service.UserService;
import com.pp.app.ui.model.response.AddressRest;
import com.pp.app.ui.model.response.OperationStatusModel;
import com.pp.app.ui.model.response.UserRest;
import com.pp.app.ui.shared.DTO.AddressDTO;
import com.pp.app.ui.shared.DTO.UserDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("users")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    AddressService addressService;

    @Autowired
    AddressService addressesService;

    @GetMapping(path = "/{id}", produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public UserRest getUser(@PathVariable String id){
        UserRest returnValue = new UserRest();
        UserDto userDto = userService.getUserByUserId(id);
        BeanUtils.copyProperties(userDto, returnValue);
        return returnValue;
    }

    @PostMapping(
            consumes = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE}
    )
    public UserRest createUser(@RequestBody UserDto userDto) throws Exception {
        UserRest userRest = new UserRest();
        BeanUtils.copyProperties(userService.createUser(userDto),userRest);
        return userRest;
    }

    @PutMapping(path = "/{id}",
            consumes = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE}
    )
    public UserDto updateUser(@PathVariable String id, @RequestBody UserDto userDto){
        return userService.updateUser(id, userDto);
    }

    @DeleteMapping(path = "/{id}")
    public OperationStatusModel deleteUser(@PathVariable String id){
        OperationStatusModel returnValue = new OperationStatusModel();

        userService.deleteUser(id);

        returnValue.setOperationName(RequestOperationName.DELETE.name());
        returnValue.setOperationResult(RequestOperationStatus.SUCCESS.name());
        return returnValue;
    }
    @GetMapping(produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public List<UserRest> getUsers(@RequestParam(value="page", defaultValue = "0") int page,
                                   @RequestParam(value="limit", defaultValue = "25") int limit){
        List<UserRest> returnValue = new ArrayList<>();

        List<UserDto> users = userService.getUsers(page,limit);

        for (UserDto userDto: users) {
            UserRest userRest = new UserRest();
            BeanUtils.copyProperties(userDto, userRest);
            returnValue.add(userRest);
        }

        return returnValue;
    }

    // http://localhost:8080/spring-boot-edu/users/idUser/addresses
    @GetMapping(path = "/{id}/addresses", produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public List<AddressRest> getUserAddresses(@PathVariable String id){
        return addressesService.getAddresses(id).stream()
                .map(element -> new ModelMapper().map(element, AddressRest.class))
                .collect(Collectors.toList());
    }

    // http://localhost:8080/spring-boot-edu/users/idUser/addresses/{addressId}
    @GetMapping(path = "/{id}/addresses/{addressId}", produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public AddressRest getUserAddress(@PathVariable String id, @PathVariable String addressId){

        AddressDTO addressDTO = addressService.getAddress(addressId);
        return new ModelMapper().map(addressDTO, AddressRest.class);
    }


}
