package com.ecommerce.jerseyverse.service.admin.impl;

import com.ecommerce.jerseyverse.dto.request.coupon.CreateCouponRequest;
import com.ecommerce.jerseyverse.dto.response.coupon.CouponDetailResponse;
import com.ecommerce.jerseyverse.entity.Coupon;
import com.ecommerce.jerseyverse.exception.BadRequestException;
import com.ecommerce.jerseyverse.exception.ConflictException;
import com.ecommerce.jerseyverse.mapper.CouponMapper;
import com.ecommerce.jerseyverse.repository.CouponRepository;
import com.ecommerce.jerseyverse.service.admin.AdminCouponService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@Transactional
public class AdminCouponServiceImpl implements AdminCouponService {

    private final CouponRepository couponRepository;

    private final CouponMapper couponMapper;


    public AdminCouponServiceImpl(CouponRepository couponRepository, CouponMapper couponMapper) {
        this.couponRepository = couponRepository;
        this.couponMapper = couponMapper;
    }

    @Override
    public CouponDetailResponse createCoupon(CreateCouponRequest request) {

        String couponCode = normalizeCouponCode(
                request.getCouponCode());

        validateDuplicateCouponCode(couponCode);

        validateDateRange(
                request.getStartDate(),
                request.getEndDate());

        Coupon coupon = couponMapper.toEntity(request);

        coupon.setCouponCode(couponCode);

        Coupon savedCoupon = couponRepository.save(coupon);

        return couponMapper.toDetailResponse(savedCoupon);
    }

    /**
     * Convert coupon code into a standard format.
     */
    private String normalizeCouponCode(String couponCode) {

        return couponCode
                .trim()
                .toUpperCase();
    }

    /**
     * Validates that the coupon code does not already exist.
     */
    private void validateDuplicateCouponCode(String couponCode) {

        if (couponRepository.existsByCouponCode(couponCode)) {
            throw new ConflictException("Coupon code already exists.");
        }
    }

    /**
     * Validates that the end date is not before the start date.
     */
    private void validateDateRange(LocalDate startDate,
                                   LocalDate endDate) {

        if (endDate.isBefore(startDate)) {
            throw new BadRequestException(
                    "End date cannot be before start date.");
        }
    }


}
