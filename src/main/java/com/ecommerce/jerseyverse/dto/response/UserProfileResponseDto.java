package com.ecommerce.jerseyverse.dto.response;

import java.util.List;

public class UserProfileResponseDto {

    private Long id;
    private String name;
    private String email;
    private String phoneNumber;
    private String role;
    private boolean active;

    private List<AddressResponseDto> addresses;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public List<AddressResponseDto> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<AddressResponseDto> addresses) {
        this.addresses = addresses;
    }
}
