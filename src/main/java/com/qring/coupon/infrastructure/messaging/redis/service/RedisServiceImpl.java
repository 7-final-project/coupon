package com.qring.coupon.infrastructure.messaging.redis.service;

import com.qring.coupon.application.v2.messaging.RedisService;
import com.qring.coupon.domain.model.CouponEntity;
import com.qring.coupon.domain.repository.RedisRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RedisServiceImpl implements RedisService {

    private final RedisRepository redisRepository;

    public void saveCoupon(Long userId, CouponEntity couponEntity) {
        redisRepository.issueRequest(userId, couponEntity.getId(), couponEntity.getTotalQuantity());
    }
}
