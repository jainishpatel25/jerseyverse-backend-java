package com.ecommerce.jerseyverse.mapper;


import com.ecommerce.jerseyverse.dto.request.coupon.CreateCouponRequest;
import com.ecommerce.jerseyverse.dto.request.coupon.UpdateCouponRequest;
import com.ecommerce.jerseyverse.dto.response.coupon.CouponDetailResponse;
import com.ecommerce.jerseyverse.dto.response.coupon.CouponSummaryResponse;
import com.ecommerce.jerseyverse.entity.Coupon;
import org.springframework.stereotype.Component;

@Component
public class CouponMapper {

    public Coupon toEntity(CreateCouponRequest request) {

        Coupon coupon = new Coupon();

        coupon.setCouponCode(request.getCouponCode().trim().toUpperCase());
        coupon.setDiscountType(request.getDiscountType());
        coupon.setDiscountValue(request.getDiscountValue());
        coupon.setMinimumOrderAmount(request.getMinimumOrderAmount());
        coupon.setMaxUses(request.getMaxUses());
//        coupon.setUsedCount(0);
        coupon.setStartDate(request.getStartDate());
        coupon.setEndDate(request.getEndDate());
        coupon.setStatus(request.getStatus());

        return coupon;
    }

    public void updateEntity(
            Coupon coupon,
            UpdateCouponRequest request) {

        coupon.setCouponCode(request.getCouponCode().trim().toUpperCase());
        coupon.setDiscountType(request.getDiscountType());
        coupon.setDiscountValue(request.getDiscountValue());
        coupon.setMinimumOrderAmount(request.getMinimumOrderAmount());
        coupon.setMaxUses(request.getMaxUses());
        coupon.setStartDate(request.getStartDate());
        coupon.setEndDate(request.getEndDate());
        coupon.setStatus(request.getStatus());
    }

    public CouponSummaryResponse toSummaryResponse(Coupon coupon) {

        CouponSummaryResponse response = new CouponSummaryResponse();

        response.setId(coupon.getId());
        response.setCouponCode(coupon.getCouponCode());
        response.setDiscountType(coupon.getDiscountType());
        response.setDiscountValue(coupon.getDiscountValue());
        response.setStatus(coupon.getStatus());
        response.setMaxUses(coupon.getMaxUses());
        response.setUsedCount(coupon.getUsedCount());
        response.setStartDate(coupon.getStartDate());
        response.setEndDate(coupon.getEndDate());

        return response;
    }

    public CouponDetailResponse toDetailResponse(Coupon coupon) {

        CouponDetailResponse response = new CouponDetailResponse();

        response.setId(coupon.getId());
        response.setCouponCode(coupon.getCouponCode());
        response.setDiscountType(coupon.getDiscountType());
        response.setDiscountValue(coupon.getDiscountValue());
        response.setMinimumOrderAmount(coupon.getMinimumOrderAmount());
        response.setMaxUses(coupon.getMaxUses());
        response.setUsedCount(coupon.getUsedCount());
        response.setStartDate(coupon.getStartDate());
        response.setEndDate(coupon.getEndDate());
        response.setStatus(coupon.getStatus());
        response.setCreatedAt(coupon.getCreatedAt());
        response.setUpdatedAt(coupon.getUpdatedAt());

        return response;
    }
}
