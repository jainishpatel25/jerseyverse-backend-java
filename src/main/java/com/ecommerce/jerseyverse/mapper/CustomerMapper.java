package com.ecommerce.jerseyverse.mapper;

import com.ecommerce.jerseyverse.dto.response.CustomerListResponse;
import com.ecommerce.jerseyverse.dto.response.CustomerOrderSummaryResponse;
import com.ecommerce.jerseyverse.dto.response.Product.CustomerDetailsResponse;
import com.ecommerce.jerseyverse.entity.Order;
import com.ecommerce.jerseyverse.entity.User;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CustomerMapper {

    public CustomerListResponse toCustomerListResponse(
            User user,
            Long totalOrders) {

        CustomerListResponse response = new CustomerListResponse();

        response.setId(user.getId());
        response.setName(user.getName());
        response.setEmail(user.getEmail());
        response.setSignupDate(user.getCreatedAt());
        response.setTotalOrders(totalOrders);

        return response;
    }

    public CustomerOrderSummaryResponse toCustomerOrderSummary(
            Order order) {

        CustomerOrderSummaryResponse response =
                new CustomerOrderSummaryResponse();

        response.setOrderNumber(order.getOrderNumber());
        response.setOrderDate(order.getCreatedAt());
        response.setStatus(order.getStatus());
        response.setTotalAmount(order.getTotalAmount());

        return response;
    }

    public CustomerDetailsResponse toCustomerDetailsResponse(
            User user,
            Long totalOrders,
            List<CustomerOrderSummaryResponse> orders) {

        CustomerDetailsResponse response =
                new CustomerDetailsResponse();

        response.setId(user.getId());
        response.setName(user.getName());
        response.setEmail(user.getEmail());
        response.setSignupDate(user.getCreatedAt());
        response.setTotalOrders(totalOrders);
        response.setOrders(orders);

        return response;
    }
}
