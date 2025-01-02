package com.qring.coupon.domain.repository;

import com.qring.coupon.domain.model.CouponEntity;

public interface CouponRepository {

    boolean existsByNameAndDeletedAtIsNull(String name);

    CouponEntity save(CouponEntity couponEntity);
}
