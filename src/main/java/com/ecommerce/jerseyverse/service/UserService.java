package com.ecommerce.jerseyverse.service;

import com.ecommerce.jerseyverse.dto.request.ChangePasswordRequestDto;
import com.ecommerce.jerseyverse.dto.request.UpdateUserRequestDto;
import com.ecommerce.jerseyverse.dto.response.UserProfileResponseDto;

public interface UserService {

    UserProfileResponseDto getCurrentUser();

    UserProfileResponseDto updateProfile(UpdateUserRequestDto request);

    void changePassword(ChangePasswordRequestDto changePasswordRequestDto);
}
