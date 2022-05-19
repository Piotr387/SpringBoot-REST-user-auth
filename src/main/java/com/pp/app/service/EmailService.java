package com.pp.app.service;

import com.pp.app.ui.shared.DTO.UserDto;

public interface EmailService {
    void sendEmail(UserDto userDto);

    boolean sendPasswordResetEmail(String firstName, String email, String token);
}
