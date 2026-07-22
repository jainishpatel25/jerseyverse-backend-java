package com.ecommerce.jerseyverse.service.admin;

import com.ecommerce.jerseyverse.dto.request.coupon.CreateCouponRequest;
import com.ecommerce.jerseyverse.dto.response.coupon.CouponDetailResponse;

public interface AdminCouponService {

    CouponDetailResponse createCoupon(CreateCouponRequest request);

}
