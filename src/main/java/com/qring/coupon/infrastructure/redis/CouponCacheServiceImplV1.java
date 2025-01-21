package com.qring.coupon.infrastructure.redis;

import com.qring.coupon.application.global.exception.BadRequestException;
import com.qring.coupon.application.v1.service.CouponCacheServiceV1;
import lombok.RequiredArgsConstructor;
import org.redisson.api.RMap;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CouponCacheServiceImplV1 implements CouponCacheServiceV1 {

    private final RedissonClient redissonClient;
    private static final String COUPON_HASH_KEY = "coupon:remain_quantity";

    public void saveCouponQuantity(Long couponId, int quantity) {
        RMap<Long, Integer> couponMap = redissonClient.getMap(COUPON_HASH_KEY);
        couponMap.put(couponId, quantity);
    }

    public void updateCouponQuantity(Long couponId, int quantity) {
        RMap<Long, Integer> couponMap = redissonClient.getMap(COUPON_HASH_KEY);
        couponMap.put(couponId, quantity);
    }

    public Integer decreaseCouponQuantity(Long couponId) {
        RMap<Long, Integer> couponMap = redissonClient.getMap(COUPON_HASH_KEY);
        int updatedQuantity = couponMap.addAndGet(couponId, -1);

        if (updatedQuantity < 0) {
            couponMap.addAndGet(couponId, 1);
            throw new BadRequestException("쿠폰이 매진되었습니다.");
        }

        return updatedQuantity;
    }
}
