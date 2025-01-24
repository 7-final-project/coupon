package com.qring.coupon.application.messaging;

import com.qring.coupon.domain.model.CouponEntity;

public interface RedisService {

    void saveCoupon(Long userId, CouponEntity couponEntity);

}
