package com.ecommerce.jerseyverse.controller;

import com.ecommerce.jerseyverse.dto.request.UpdateUserRequestDto;
import com.ecommerce.jerseyverse.dto.response.UserProfileResponseDto;
import com.ecommerce.jerseyverse.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/me")
    public ResponseEntity<UserProfileResponseDto> getCurrentUser() {

        return ResponseEntity.ok(
                userService.getCurrentUser()
        );
    }

    @PutMapping("/me")
    public ResponseEntity<UserProfileResponseDto> updateProfile(
            @Valid @RequestBody UpdateUserRequestDto request) {

        return ResponseEntity.ok(
                userService.updateProfile(request)
        );
    }

}