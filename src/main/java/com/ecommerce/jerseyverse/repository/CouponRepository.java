package com.ecommerce.jerseyverse.repository;

import com.ecommerce.jerseyverse.entity.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CouponRepository extends JpaRepository<Coupon, Long> {

    Optional<Coupon> findByCouponCode(String couponCode);

    boolean existsByCouponCode(String couponCode);

    boolean existsByCouponCodeAndIdNot(String couponCode, Long id);

}
