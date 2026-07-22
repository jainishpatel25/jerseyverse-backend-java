package com.ecommerce.jerseyverse.dto.request.coupon;

import com.ecommerce.jerseyverse.enums.CouponStatus;
import jakarta.validation.constraints.NotNull;

public class UpdateCouponStatusRequest {

    @NotNull(message = "Status is required.")
    private CouponStatus status;

    public UpdateCouponStatusRequest() {
    }

    public CouponStatus getStatus() {
        return status;
    }

    public void setStatus(CouponStatus status) {
        this.status = status;
    }
}
