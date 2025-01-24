package com.qring.coupon.application.v2.messaging;

import com.qring.coupon.domain.model.CouponEntity;

public interface RedisService {

    void saveCoupon(Long userId, CouponEntity couponEntity);

}
