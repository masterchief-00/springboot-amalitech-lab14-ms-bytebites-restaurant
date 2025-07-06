package com.kwizera.authservice.services;

import com.kwizera.authservice.domain.dtos.LoginRequestDTO;
import com.kwizera.authservice.domain.dtos.RegisterRequestDTO;

public interface UserServices {
    String register(RegisterRequestDTO request);

    String login(LoginRequestDTO request);
}
