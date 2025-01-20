package com.qring.coupon.infrastructure.redis;

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

    public Integer getCouponQuantity(Long couponId) {
        RMap<Long, Integer> couponMap = redissonClient.getMap(COUPON_HASH_KEY);
        return couponMap.getOrDefault(couponId, 0);
    }

    public void updateCouponQuantity(Long couponId, int quantity) {
        RMap<Long, Integer> couponMap = redissonClient.getMap(COUPON_HASH_KEY);
        couponMap.put(couponId, quantity);
    }
}
