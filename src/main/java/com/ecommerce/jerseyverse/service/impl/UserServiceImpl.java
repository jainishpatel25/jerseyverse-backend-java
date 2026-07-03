package com.ecommerce.jerseyverse.service.impl;

import com.ecommerce.jerseyverse.dto.request.ChangePasswordRequestDto;
import com.ecommerce.jerseyverse.dto.request.UpdateUserRequestDto;
import com.ecommerce.jerseyverse.dto.response.UserProfileResponseDto;
import com.ecommerce.jerseyverse.entity.User;
import com.ecommerce.jerseyverse.exception.BadRequestException;
import com.ecommerce.jerseyverse.exception.ConflictException;
import com.ecommerce.jerseyverse.exception.UnauthorizedException;
import com.ecommerce.jerseyverse.mapper.UserMapper;
import com.ecommerce.jerseyverse.repository.UserRepository;
import com.ecommerce.jerseyverse.security.userdetails.CustomUserDetails;
import com.ecommerce.jerseyverse.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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

    private User getAuthenticatedUser() {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        CustomUserDetails userDetails =
                (CustomUserDetails) authentication.getPrincipal();

        return userDetails.getUser();
    }

    @Override
    public UserProfileResponseDto getCurrentUser() {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        CustomUserDetails userDetails =
                (CustomUserDetails) authentication.getPrincipal();

        User user = userDetails.getUser();

        return UserMapper.toUserProfileResponse(user);
    }

    @Override
    public UserProfileResponseDto updateProfile(UpdateUserRequestDto request) {

        User currentUser = getAuthenticatedUser();

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
        User currentUser = getAuthenticatedUser();

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
