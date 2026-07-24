package com.ecommerce.jerseyverse.service.admin;

import com.ecommerce.jerseyverse.dto.response.CustomerListResponse;
import com.ecommerce.jerseyverse.dto.response.PageResponse;
import com.ecommerce.jerseyverse.dto.response.Product.CustomerDetailsResponse;

public interface AdminCustomerManagementService {


    PageResponse<CustomerListResponse> getCustomers(
            int page,
            int size,
            String search
    );

    CustomerDetailsResponse getCustomerDetails(
            Long customerId
    );

}
