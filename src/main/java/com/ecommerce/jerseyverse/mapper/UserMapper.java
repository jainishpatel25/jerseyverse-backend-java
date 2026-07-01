package com.ecommerce.jerseyverse.mapper;

import com.ecommerce.jerseyverse.dto.request.RegisterRequestDto;
import com.ecommerce.jerseyverse.dto.response.RegisterResponseDto;
import com.ecommerce.jerseyverse.entity.User;

public class UserMapper {

    private UserMapper() {
        // Prevent instantiation
    }

    public static User toEntity(RegisterRequestDto request) {

        User user = new User();

        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPhoneNumber(request.getPhoneNumber());

        return user;
    }

    public static RegisterResponseDto toRegisterResponse(User user) {

        RegisterResponseDto response = new RegisterResponseDto();

        response.setId(user.getId());
        response.setName(user.getName());
        response.setEmail(user.getEmail());
        response.setRole(user.getRole().name());
        response.setMessage("User registered successfully.");

        return response;
    }

}