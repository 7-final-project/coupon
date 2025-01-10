package com.qring.coupon.infrastructure.scheduler;

import com.qring.coupon.domain.model.CouponEntity;

public interface CouponScheduler {

    void scheduleIssuanceStatus(CouponEntity couponEntity);

}
