package com.qring.coupon.application.v1.service;

public interface CouponCacheServiceV1 {

    void saveCouponQuantity(Long couponId, int quantity);

    Integer getCouponQuantity(Long couponId);

    void updateCouponQuantity(Long couponId, int quantity);

}
