package com.qring.coupon.domain.repository;

import com.qring.coupon.domain.model.CouponEntity;

import java.util.Optional;

public interface CouponRepository {

    Optional<CouponEntity> findByIdAndDeletedAtIsNull(Long id);

    boolean existsByNameAndDeletedAtIsNull(String name);

    boolean existsByNameAndIdNotAndDeletedAtIsNull(String name, Long id);

    CouponEntity save(CouponEntity couponEntity);

}
