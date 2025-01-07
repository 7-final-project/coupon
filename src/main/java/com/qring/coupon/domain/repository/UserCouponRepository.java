package com.qring.coupon.domain.repository;

import com.qring.coupon.domain.model.CouponEntity;
import com.qring.coupon.domain.model.UserCouponEntity;

public interface UserCouponRepository {

    boolean existsByUserIdAndCouponEntity(Long userId, CouponEntity couponEntity);

    UserCouponEntity save(UserCouponEntity userCouponEntity);

}
