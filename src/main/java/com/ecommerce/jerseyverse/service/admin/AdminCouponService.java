package com.ecommerce.jerseyverse.service.admin;

import com.ecommerce.jerseyverse.dto.request.coupon.ApplyCouponRequest;
import com.ecommerce.jerseyverse.dto.request.coupon.CreateCouponRequest;
import com.ecommerce.jerseyverse.dto.request.coupon.UpdateCouponRequest;
import com.ecommerce.jerseyverse.dto.request.coupon.UpdateCouponStatusRequest;
import com.ecommerce.jerseyverse.dto.response.PageResponse;
import com.ecommerce.jerseyverse.dto.response.cart.CartResponse;
import com.ecommerce.jerseyverse.dto.response.coupon.CouponDetailResponse;
import com.ecommerce.jerseyverse.dto.response.coupon.CouponSummaryResponse;

public interface AdminCouponService {

    CouponDetailResponse createCoupon(CreateCouponRequest request);

    PageResponse<CouponSummaryResponse> getAllCoupons(
            int page,
            int size,
            String sortBy,
            String sortDir);

    CouponDetailResponse getCouponById(Long couponId);

    CouponDetailResponse updateCoupon(
            Long couponId,
            UpdateCouponRequest request);

    void deleteCoupon(Long couponId);

    CouponDetailResponse updateCouponStatus(
            Long couponId,
            UpdateCouponStatusRequest request);

    CartResponse applyCoupon(
            ApplyCouponRequest request);

}
