package com.ecommerce.jerseyverse.service.impl;

import com.ecommerce.jerseyverse.dto.request.LoginRequestDto;
import com.ecommerce.jerseyverse.dto.request.RegisterRequestDto;
import com.ecommerce.jerseyverse.dto.response.LoginResponseDto;
import com.ecommerce.jerseyverse.dto.response.RegisterResponseDto;
import com.ecommerce.jerseyverse.entity.User;
import com.ecommerce.jerseyverse.enums.Role;
import com.ecommerce.jerseyverse.exception.ConflictException;
import com.ecommerce.jerseyverse.mapper.UserMapper;
import com.ecommerce.jerseyverse.repository.UserRepository;
import com.ecommerce.jerseyverse.service.AuthService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    public AuthServiceImpl(UserRepository userRepository,
                           PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public RegisterResponseDto register(RegisterRequestDto request) {

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new ConflictException("Email is already registered.");
        }

        if (userRepository.existsByPhoneNumber(request.getPhoneNumber())) {
            throw new ConflictException("Phone number is already registered.");
        }

        User user = UserMapper.toEntity(request);

        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(Role.ROLE_USER);
        user.setActive(true);

        User savedUser = userRepository.save(user);

        return UserMapper.toRegisterResponse(savedUser);
    }

    @Override
    public LoginResponseDto login(LoginRequestDto request) {
        return null;
    }
}
