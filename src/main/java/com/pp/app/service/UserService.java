package com.pp.app.service;

import com.pp.app.ui.shared.DTO.UserDto;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {

    UserDto createUser(UserDto userDto);
    UserDto getUser(String email);
    UserDto getUserByUserId(String id);
    UserDto updateUser(String userId, UserDto userDto);
    void deleteUser(String userId);
    List<UserDto> getUsers(int page, int limit);
    boolean verifyEmailToken(String token);

    boolean requestPasswordReset(String email);

    boolean resetPassword(String token, String password);
}
