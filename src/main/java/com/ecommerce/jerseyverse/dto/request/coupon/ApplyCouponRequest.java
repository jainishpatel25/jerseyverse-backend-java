package com.ecommerce.jerseyverse.dto.request.coupon;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class ApplyCouponRequest {

    @NotBlank(message = "Coupon code is required.")
    @Size(max = 50, message = "Coupon code cannot exceed 50 characters.")
    private String couponCode;

    public ApplyCouponRequest() {
    }

    public String getCouponCode() {
        return couponCode;
    }

    public void setCouponCode(String couponCode) {
        this.couponCode = couponCode;
    }
}
