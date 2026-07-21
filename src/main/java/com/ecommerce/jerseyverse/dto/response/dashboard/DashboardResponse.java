package com.ecommerce.jerseyverse.dto.response.dashboard;

import java.math.BigDecimal;

public class DashboardResponse {

    private Long totalOrders;
    private Long totalProducts;
    private BigDecimal totalRevenue;

    public DashboardResponse() {
    }

    public DashboardResponse(Long totalOrders, Long totalProducts, BigDecimal totalRevenue) {
        this.totalOrders = totalOrders;
        this.totalProducts = totalProducts;
        this.totalRevenue = totalRevenue;
    }

    public Long getTotalOrders() {
        return totalOrders;
    }

    public void setTotalOrders(Long totalOrders) {
        this.totalOrders = totalOrders;
    }

    public Long getTotalProducts() {
        return totalProducts;
    }

    public void setTotalProducts(Long totalProducts) {
        this.totalProducts = totalProducts;
    }

    public BigDecimal getTotalRevenue() {
        return totalRevenue;
    }

    public void setTotalRevenue(BigDecimal totalRevenue) {
        this.totalRevenue = totalRevenue;
    }

}
