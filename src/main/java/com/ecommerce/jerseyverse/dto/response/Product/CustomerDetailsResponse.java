package com.ecommerce.jerseyverse.dto.response.Product;

import com.ecommerce.jerseyverse.dto.response.CustomerOrderSummaryResponse;

import java.time.LocalDateTime;
import java.util.List;

public class CustomerDetailsResponse {

    private Long id;
    private String name;
    private String email;
    private LocalDateTime signupDate;
    private Long totalOrders;

    private List<CustomerOrderSummaryResponse> orders;

    public List<CustomerOrderSummaryResponse> getOrders() {
        return orders;
    }

    public void setOrders(List<CustomerOrderSummaryResponse> orders) {
        this.orders = orders;
    }

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

    public LocalDateTime getSignupDate() {
        return signupDate;
    }

    public void setSignupDate(LocalDateTime signupDate) {
        this.signupDate = signupDate;
    }

    public Long getTotalOrders() {
        return totalOrders;
    }

    public void setTotalOrders(Long totalOrders) {
        this.totalOrders = totalOrders;
    }
}
