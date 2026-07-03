package com.ecommerce.jerseyverse.service.impl;

import com.ecommerce.jerseyverse.dto.request.CreateAddressRequestDto;
import com.ecommerce.jerseyverse.dto.request.UpdateAddressRequestDto;
import com.ecommerce.jerseyverse.dto.response.AddressResponseDto;
import com.ecommerce.jerseyverse.entity.Address;
import com.ecommerce.jerseyverse.entity.User;
import com.ecommerce.jerseyverse.exception.ResourceNotFoundException;
import com.ecommerce.jerseyverse.mapper.AddressMapper;
import com.ecommerce.jerseyverse.repository.AddressRepository;
import com.ecommerce.jerseyverse.repository.UserRepository;
import com.ecommerce.jerseyverse.security.userdetails.CustomUserDetails;
import com.ecommerce.jerseyverse.service.AddressService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressServiceImpl implements AddressService {

    private final AddressRepository addressRepository;

    private final UserRepository userRepository;

    public AddressServiceImpl(AddressRepository addressRepository, UserRepository userRepository) {
        this.addressRepository = addressRepository;
        this.userRepository = userRepository;
    }

    private User getAuthenticatedUser() {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        CustomUserDetails userDetails =
                (CustomUserDetails) authentication.getPrincipal();

        return userDetails.getUser();
    }

    private Address getUserAddress(Long addressId) {

        User currentUser = getAuthenticatedUser();

        return addressRepository
                .findByIdAndUser(addressId, currentUser)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Address not found."));
    }

    @Override
    public AddressResponseDto createAddress(CreateAddressRequestDto request) {

        User currentUser = getAuthenticatedUser();

        if (request.isDefault()) {

            addressRepository.findByUserAndIsDefaultTrue(currentUser)
                    .ifPresent(address -> {
                        address.setDefault(false);
                        addressRepository.save(address);
                    });
        }

        Address address = AddressMapper.toEntity(request);

        address.setUser(currentUser);

        Address savedAddress = addressRepository.save(address);

        return AddressMapper.toResponse(savedAddress);
    }

    @Override
    public List<AddressResponseDto> getAllAddresses() {

        User currentUser = getAuthenticatedUser();

        return addressRepository.findByUser(currentUser)
                .stream()
                .map(AddressMapper::toResponse)
                .toList();
    }

    @Override
    public AddressResponseDto getAddressById(Long addressId) {

        Address address = getUserAddress(addressId);

        return AddressMapper.toResponse(address);
    }

    @Override
    public AddressResponseDto updateAddress(
            Long addressId,
            UpdateAddressRequestDto request) {

        Address address = getUserAddress(addressId);

        AddressMapper.updateEntity(address, request);

        Address updatedAddress = addressRepository.save(address);

        return AddressMapper.toResponse(updatedAddress);
    }

    @Override
    public void setDefaultAddress(Long addressId) {

        User currentUser = getAuthenticatedUser();

        Address selectedAddress = getUserAddress(addressId);

        addressRepository.findByUserAndIsDefaultTrue(currentUser)
                .ifPresent(address -> {

                    if (!address.getId().equals(selectedAddress.getId())) {

                        address.setDefault(false);

                        addressRepository.save(address);
                    }

                });

        selectedAddress.setDefault(true);

        addressRepository.save(selectedAddress);
    }
}
