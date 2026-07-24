package com.ecommerce.jerseyverse.controller.admin;

import com.ecommerce.jerseyverse.dto.response.CustomerListResponse;
import com.ecommerce.jerseyverse.dto.response.PageResponse;
import com.ecommerce.jerseyverse.dto.response.Product.CustomerDetailsResponse;
import com.ecommerce.jerseyverse.service.admin.AdminCustomerManagementService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/admin/customer")
public class AdminCustomerController {

    private final AdminCustomerManagementService adminCustomerManagementService;

    public AdminCustomerController(AdminCustomerManagementService adminCustomerManagementService) {
        this.adminCustomerManagementService = adminCustomerManagementService;
    }


    @GetMapping
    public ResponseEntity<PageResponse<CustomerListResponse>> getCustomers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false, defaultValue = "") String search) {

        return ResponseEntity.ok(
                adminCustomerManagementService.getCustomers(
                        page,
                        size,
                        search
                )
        );
    }

    @GetMapping("/{customerId}")
    public ResponseEntity<CustomerDetailsResponse> getCustomerDetails(
            @PathVariable Long customerId) {

        return ResponseEntity.ok(
                adminCustomerManagementService.getCustomerDetails(customerId)
        );
    }

}
