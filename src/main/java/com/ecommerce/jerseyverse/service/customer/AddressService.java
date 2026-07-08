package com.ecommerce.jerseyverse.service.customer;

import com.ecommerce.jerseyverse.dto.request.CreateAddressRequestDto;
import com.ecommerce.jerseyverse.dto.request.UpdateAddressRequestDto;
import com.ecommerce.jerseyverse.dto.response.AddressResponseDto;

import java.util.List;

public interface AddressService {

    AddressResponseDto createAddress(CreateAddressRequestDto request);

    List<AddressResponseDto> getAllAddresses();

    AddressResponseDto getAddressById(Long addressId);

    AddressResponseDto updateAddress(
            Long addressId,
            UpdateAddressRequestDto request);

    void setDefaultAddress(Long addressId);

}
