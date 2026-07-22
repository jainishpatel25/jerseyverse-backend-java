package com.ecommerce.jerseyverse.service.admin.impl;

import com.ecommerce.jerseyverse.dto.request.coupon.ApplyCouponRequest;
import com.ecommerce.jerseyverse.dto.request.coupon.CreateCouponRequest;
import com.ecommerce.jerseyverse.dto.request.coupon.UpdateCouponRequest;
import com.ecommerce.jerseyverse.dto.request.coupon.UpdateCouponStatusRequest;
import com.ecommerce.jerseyverse.dto.response.cart.CartResponse;
import com.ecommerce.jerseyverse.dto.response.coupon.CouponDetailResponse;
import com.ecommerce.jerseyverse.dto.response.coupon.CouponSummaryResponse;
import com.ecommerce.jerseyverse.entity.Cart;
import com.ecommerce.jerseyverse.entity.Coupon;
import com.ecommerce.jerseyverse.entity.User;
import com.ecommerce.jerseyverse.enums.CouponStatus;
import com.ecommerce.jerseyverse.enums.DiscountType;
import com.ecommerce.jerseyverse.exception.BadRequestException;
import com.ecommerce.jerseyverse.exception.ConflictException;
import com.ecommerce.jerseyverse.mapper.CartMapper;
import com.ecommerce.jerseyverse.mapper.CouponMapper;
import com.ecommerce.jerseyverse.repository.CartRepository;
import com.ecommerce.jerseyverse.repository.CouponRepository;
import com.ecommerce.jerseyverse.security.utils.SecurityUtils;
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

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;

@Service
@Transactional
public class AdminCouponServiceImpl implements AdminCouponService {

    private final CouponRepository couponRepository;

    private final CouponMapper couponMapper;

    private final SecurityUtils securityUtils;

    private final CartRepository cartRepository;

    private final CartMapper cartMapper;


    public AdminCouponServiceImpl(CouponRepository couponRepository, CouponMapper couponMapper, SecurityUtils securityUtils, CartRepository cartRepository, CartMapper cartMapper) {
        this.couponRepository = couponRepository;
        this.couponMapper = couponMapper;
        this.securityUtils = securityUtils;
        this.cartRepository = cartRepository;
        this.cartMapper = cartMapper;
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

    @Override
    @Transactional
    public void deleteCoupon(Long couponId) {

        Coupon coupon = getCouponByIdOrThrow(couponId);

        validateCouponDeletion(coupon);

        couponRepository.delete(coupon);
    }

    private void validateCouponDeletion(Coupon coupon) {

        if (coupon.getUsedCount() > 0) {
            throw new ConflictException(
                    "Coupon cannot be deleted because it has already been used.");
        }
    }

    @Override
    @Transactional
    public CouponDetailResponse updateCouponStatus(
            Long couponId,
            UpdateCouponStatusRequest request) {

        Coupon coupon = getCouponByIdOrThrow(couponId);

        coupon.setStatus(request.getStatus());

        Coupon updatedCoupon = couponRepository.save(coupon);

        return couponMapper.toDetailResponse(updatedCoupon);
    }

    @Override
    @Transactional
    public CartResponse applyCoupon(
            ApplyCouponRequest request) {

        User user = securityUtils.getCurrentUser();

        Cart cart = getCartByUser(user);

        Coupon coupon = getCouponByCode(
                request.getCouponCode());

        BigDecimal subtotal =
                calculateCartSubtotal(cart);

        validateCouponApplication(
                coupon,
                cart,
                subtotal);

        BigDecimal discount =
                calculateDiscount(
                        coupon,
                        subtotal);

        cart.setAppliedCouponCode(
                coupon.getCouponCode());

        cart.setDiscountAmount(discount);

        Cart updatedCart =
                cartRepository.save(cart);

        return cartMapper.toCartResponse(updatedCart);
    }

    private Cart getCartByUser(User user) {

        return cartRepository.findByUser(user)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Cart not found."));
    }

    private Coupon getCouponByCode(String couponCode) {

        String normalizedCouponCode =
                normalizeCouponCode(couponCode);

        return couponRepository.findByCouponCode(normalizedCouponCode)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Coupon not found."));
    }

    private void validateCouponApplication(
            Coupon coupon,
            Cart cart,
            BigDecimal subtotal) {

        validateCart(cart);

        validateCouponNotAlreadyApplied(cart);

        validateCouponStatus(coupon);

        validateCouponValidity(coupon);

        validateCouponUsageLimit(coupon);

        validateMinimumOrderAmount(coupon, subtotal);
    }

    private void validateCart(Cart cart) {

        if (cart.getCartItems().isEmpty()) {
            throw new BadRequestException(
                    "Cannot apply coupon to an empty cart.");
        }
    }

    private void validateCouponNotAlreadyApplied(
            Cart cart) {

        if (cart.getAppliedCouponCode() != null && !cart.getAppliedCouponCode().isBlank()) {
            throw new BadRequestException(
                    "A coupon has already been applied to this cart.");
        }
    }

    private void validateCouponStatus(Coupon coupon) {

        if (coupon.getStatus() != CouponStatus.ACTIVE) {
            throw new BadRequestException(
                    "Coupon is inactive.");
        }
    }

    private void validateCouponValidity(Coupon coupon) {

        LocalDate today = LocalDate.now();

        if (today.isBefore(coupon.getStartDate())) {
            throw new BadRequestException(
                    "Coupon is not yet active.");
        }

        if (today.isAfter(coupon.getEndDate())) {
            throw new BadRequestException(
                    "Coupon has expired.");
        }
    }

    private void validateCouponUsageLimit(Coupon coupon) {

        if (coupon.getUsedCount() >= coupon.getMaxUses()) {
            throw new BadRequestException(
                    "Coupon usage limit has been reached.");
        }
    }

    private void validateMinimumOrderAmount(
            Coupon coupon,
            BigDecimal subtotal) {

        if (subtotal.compareTo(
                coupon.getMinimumOrderAmount()) < 0) {

            throw new BadRequestException(
                    "Minimum order amount for this coupon is ₹"
                            + coupon.getMinimumOrderAmount() + ".");
        }
    }

    private BigDecimal calculateDiscount(
            Coupon coupon,
            BigDecimal subtotal) {

        if (coupon.getDiscountType() == DiscountType.FLAT) {

            return coupon.getDiscountValue()
                    .min(subtotal);
        }

        return subtotal
                .multiply(coupon.getDiscountValue())
                .divide(
                        BigDecimal.valueOf(100),
                        2,
                        RoundingMode.HALF_UP);
    }

    private BigDecimal calculateCartSubtotal(Cart cart) {

        return cart.getCartItems()
                .stream()
                .map(cartItem -> {

                    BigDecimal unitPrice =
                            cartItem.getProductVariant()
                                    .getProduct()
                                    .getPrice();

                    return unitPrice.multiply(
                            BigDecimal.valueOf(
                                    cartItem.getQuantity()));
                })
                .reduce(
                        BigDecimal.ZERO,
                        BigDecimal::add);
    }

}
