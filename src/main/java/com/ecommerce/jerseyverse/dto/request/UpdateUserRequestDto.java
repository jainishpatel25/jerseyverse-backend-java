package com.ecommerce.jerseyverse.dto.request;

import jakarta.validation.constraints.*;

public class UpdateUserRequestDto {

    @NotBlank(message = "Name is required.")
    @Size(max = 100)
    private String name;

    @NotBlank(message = "Phone number is required.")
    @Pattern(
            regexp = "^[6-9]\\d{9}$",
            message = "Invalid phone number."
    )
    private String phoneNumber;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
