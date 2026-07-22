package com.ecommerce.jerseyverse.service.admin.impl;

import com.ecommerce.jerseyverse.dto.request.coupon.CreateCouponRequest;
import com.ecommerce.jerseyverse.dto.request.coupon.UpdateCouponRequest;
import com.ecommerce.jerseyverse.dto.response.coupon.CouponDetailResponse;
import com.ecommerce.jerseyverse.dto.response.coupon.CouponSummaryResponse;
import com.ecommerce.jerseyverse.entity.Coupon;
import com.ecommerce.jerseyverse.exception.BadRequestException;
import com.ecommerce.jerseyverse.exception.ConflictException;
import com.ecommerce.jerseyverse.mapper.CouponMapper;
import com.ecommerce.jerseyverse.repository.CouponRepository;
import com.ecommerce.jerseyverse.service.admin.AdminCouponService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ecommerce.jerseyverse.dto.response.PageResponse;
import com.ecommerce.jerseyverse.exception.ResourceNotFoundException;
import com.ecommerce.jerseyverse.util.PaginationUtils;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

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

    @Override
    @Transactional(readOnly = true)
    public CouponDetailResponse getCouponById(Long couponId) {

        Coupon coupon = getCouponByIdOrThrow(couponId);

        return couponMapper.toDetailResponse(coupon);
    }

    @Override
    @Transactional(readOnly = true)
    public PageResponse<CouponSummaryResponse> getAllCoupons(
            int page,
            int size,
            String sortBy,
            String sortDir) {

        Sort sort = sortDir.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        Pageable pageable =
                PageRequest.of(page, size, sort);

        Page<Coupon> couponPage =
                couponRepository.findAll(pageable);

        Page<CouponSummaryResponse> responsePage =
                couponPage.map(couponMapper::toSummaryResponse);

        return PaginationUtils.buildPageResponse(responsePage);
    }

    private Coupon getCouponByIdOrThrow(Long couponId) {

        return couponRepository.findById(couponId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Coupon not found with ID: " + couponId));
    }

    @Override
    @Transactional
    public CouponDetailResponse updateCoupon(
            Long couponId,
            UpdateCouponRequest request) {

        Coupon coupon = getCouponByIdOrThrow(couponId);

        String couponCode = normalizeCouponCode(
                request.getCouponCode());

        validateDuplicateCouponCodeForUpdate(
                couponCode,
                couponId);

        validateDateRange(
                request.getStartDate(),
                request.getEndDate());

        couponMapper.updateEntity(coupon, request);

        coupon.setCouponCode(couponCode);

        Coupon updatedCoupon =
                couponRepository.save(coupon);

        return couponMapper.toDetailResponse(updatedCoupon);
    }

    private void validateDuplicateCouponCodeForUpdate(
            String couponCode,
            Long couponId) {

        if (couponRepository.existsByCouponCodeAndIdNot(
                couponCode,
                couponId)) {

            throw new ConflictException(
                    "Coupon code already exists.");
        }
    }
}
