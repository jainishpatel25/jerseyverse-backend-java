package com.ecommerce.jerseyverse.service.customer.impl;

import com.ecommerce.jerseyverse.dto.request.ChangePasswordRequestDto;
import com.ecommerce.jerseyverse.dto.request.UpdateUserRequestDto;
import com.ecommerce.jerseyverse.dto.response.UserProfileResponseDto;
import com.ecommerce.jerseyverse.entity.User;
import com.ecommerce.jerseyverse.exception.BadRequestException;
import com.ecommerce.jerseyverse.exception.ConflictException;
import com.ecommerce.jerseyverse.exception.UnauthorizedException;
import com.ecommerce.jerseyverse.mapper.UserMapper;
import com.ecommerce.jerseyverse.repository.UserRepository;
import com.ecommerce.jerseyverse.security.utils.SecurityUtils;
import com.ecommerce.jerseyverse.service.customer.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder){
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    public UserProfileResponseDto getCurrentUser() {

        User user = SecurityUtils.getCurrentUser();

        return UserMapper.toUserProfileResponse(user);
    }

    @Override
    public UserProfileResponseDto updateProfile(UpdateUserRequestDto request) {

        User currentUser = SecurityUtils.getCurrentUser();

        if (!currentUser.getPhoneNumber().equals(request.getPhoneNumber())
                && userRepository.existsByPhoneNumber(request.getPhoneNumber())) {

            throw new ConflictException("Phone number is already registered.");
        }

        UserMapper.updateUser(currentUser, request);

        User updatedUser = userRepository.save(currentUser);

        return UserMapper.toUserProfileResponse(updatedUser);
    }

    @Override
    public void changePassword(ChangePasswordRequestDto request) {

        User currentUser = SecurityUtils.getCurrentUser();

        if (!passwordEncoder.matches(
                request.getCurrentPassword(),
                currentUser.getPassword())) {

            throw new UnauthorizedException("Current password is incorrect.");
        }

        if (passwordEncoder.matches(
                request.getNewPassword(),
                currentUser.getPassword())) {

            throw new BadRequestException(
                    "New password must be different from the current password.");
        }

        currentUser.setPassword(
                passwordEncoder.encode(request.getNewPassword()));

        userRepository.save(currentUser);
    }
}
