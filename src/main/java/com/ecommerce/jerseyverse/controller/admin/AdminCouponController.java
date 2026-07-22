package com.ecommerce.jerseyverse.controller.admin;

import com.ecommerce.jerseyverse.dto.request.coupon.CreateCouponRequest;
import com.ecommerce.jerseyverse.dto.request.coupon.UpdateCouponRequest;
import com.ecommerce.jerseyverse.dto.response.PageResponse;
import com.ecommerce.jerseyverse.dto.response.coupon.CouponDetailResponse;
import com.ecommerce.jerseyverse.dto.response.coupon.CouponSummaryResponse;
import com.ecommerce.jerseyverse.service.admin.AdminCouponService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/admin/coupons")
public class AdminCouponController {

    private final AdminCouponService couponService;

    public AdminCouponController(AdminCouponService couponService) {
        this.couponService = couponService;
    }

    @PostMapping
    public ResponseEntity<CouponDetailResponse> createCoupon(
            @Valid @RequestBody CreateCouponRequest request) {

        CouponDetailResponse response =
                couponService.createCoupon(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping
    public ResponseEntity<PageResponse<CouponSummaryResponse>> getAllCoupons(

            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDir) {

        PageResponse<CouponSummaryResponse> response =
                couponService.getAllCoupons(page, size, sortBy, sortDir);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{couponId}")
    public ResponseEntity<CouponDetailResponse> getCouponById(
            @PathVariable Long couponId) {

        CouponDetailResponse response =
                couponService.getCouponById(couponId);

        return ResponseEntity.ok(response);
    }

    @PutMapping("/{couponId}")
    public ResponseEntity<CouponDetailResponse> updateCoupon(
            @PathVariable Long couponId,
            @Valid @RequestBody UpdateCouponRequest request) {

        CouponDetailResponse response =
                couponService.updateCoupon(couponId, request);

        return ResponseEntity.ok(response);
    }
}
