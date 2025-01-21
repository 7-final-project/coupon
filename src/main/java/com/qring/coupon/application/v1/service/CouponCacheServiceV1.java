package com.qring.coupon.application.v1.service;

public interface CouponCacheServiceV1 {

    void saveCouponQuantity(Long couponId, int quantity);

    void updateCouponQuantity(Long couponId, int quantity);

    Integer decreaseCouponQuantity(Long couponId);

}
