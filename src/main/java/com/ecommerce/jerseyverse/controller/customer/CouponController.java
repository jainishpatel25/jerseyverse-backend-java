package com.ecommerce.jerseyverse.controller.customer;

import com.ecommerce.jerseyverse.dto.request.coupon.ApplyCouponRequest;
import com.ecommerce.jerseyverse.dto.response.cart.CartResponse;
import com.ecommerce.jerseyverse.service.admin.AdminCouponService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/coupons")
public class CouponController {

    private final AdminCouponService couponService;

    public CouponController(AdminCouponService couponService) {
        this.couponService = couponService;
    }

    @PostMapping("/apply")
    public ResponseEntity<CartResponse> applyCoupon(
            @Valid @RequestBody ApplyCouponRequest request) {

        CartResponse response =
                couponService.applyCoupon(request);

        return ResponseEntity.ok(response);
    }
}
