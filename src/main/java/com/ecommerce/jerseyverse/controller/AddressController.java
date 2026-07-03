package com.ecommerce.jerseyverse.controller;

import com.ecommerce.jerseyverse.dto.request.CreateAddressRequestDto;
import com.ecommerce.jerseyverse.dto.request.UpdateAddressRequestDto;
import com.ecommerce.jerseyverse.dto.response.AddressResponseDto;
import com.ecommerce.jerseyverse.service.AddressService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/addresses")
public class AddressController {

    private final AddressService addressService;

    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }

    @PostMapping
    public ResponseEntity<AddressResponseDto> createAddress(
            @Valid @RequestBody CreateAddressRequestDto request) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(addressService.createAddress(request));
    }

    @GetMapping
    public ResponseEntity<List<AddressResponseDto>> getAllAddresses() {

        return ResponseEntity.ok(
                addressService.getAllAddresses());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AddressResponseDto> getAddressById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                addressService.getAddressById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AddressResponseDto> updateAddress(
            @PathVariable Long id,
            @Valid @RequestBody UpdateAddressRequestDto request) {

        return ResponseEntity.ok(
                addressService.updateAddress(id, request));
    }

    @PatchMapping("/{id}/default")
    public ResponseEntity<Void> setDefaultAddress(
            @PathVariable Long id) {

        addressService.setDefaultAddress(id);

        return ResponseEntity.noContent().build();
    }
}
