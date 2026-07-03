package com.ecommerce.jerseyverse.mapper;

import com.ecommerce.jerseyverse.dto.request.CreateAddressRequestDto;
import com.ecommerce.jerseyverse.dto.request.UpdateAddressRequestDto;
import com.ecommerce.jerseyverse.dto.response.AddressResponseDto;
import com.ecommerce.jerseyverse.entity.Address;

public class AddressMapper {

    private AddressMapper() {
    }

    public static Address toEntity(CreateAddressRequestDto request) {

        Address address = new Address();

        address.setFullName(request.getFullName());
        address.setPhoneNumber(request.getPhoneNumber());
        address.setAddressLine1(request.getAddressLine1());
        address.setAddressLine2(request.getAddressLine2());
        address.setCity(request.getCity());
        address.setState(request.getState());
        address.setCountry(request.getCountry());
        address.setPostalCode(request.getPostalCode());
        address.setDefault(request.isDefault());

        return address;
    }

    public static void updateEntity(
            Address address,
            UpdateAddressRequestDto request) {

        address.setFullName(request.getFullName());
        address.setPhoneNumber(request.getPhoneNumber());
        address.setAddressLine1(request.getAddressLine1());
        address.setAddressLine2(request.getAddressLine2());
        address.setCity(request.getCity());
        address.setState(request.getState());
        address.setCountry(request.getCountry());
        address.setPostalCode(request.getPostalCode());

    }

    public static AddressResponseDto toResponse(Address address) {

        AddressResponseDto response = new AddressResponseDto();

        response.setId(address.getId());
        response.setFullName(address.getFullName());
        response.setPhoneNumber(address.getPhoneNumber());
        response.setAddressLine1(address.getAddressLine1());
        response.setAddressLine2(address.getAddressLine2());
        response.setCity(address.getCity());
        response.setState(address.getState());
        response.setCountry(address.getCountry());
        response.setPostalCode(address.getPostalCode());
        response.setDefault(address.isDefault());

        return response;
    }
}
