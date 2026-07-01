package com.ecommerce.jerseyverse.service;

import com.ecommerce.jerseyverse.dto.request.LoginRequestDto;
import com.ecommerce.jerseyverse.dto.request.RegisterRequestDto;
import com.ecommerce.jerseyverse.dto.response.LoginResponseDto;
import com.ecommerce.jerseyverse.dto.response.RegisterResponseDto;

public interface AuthService {

    RegisterResponseDto register(RegisterRequestDto request);

    LoginResponseDto login(LoginRequestDto request);

}