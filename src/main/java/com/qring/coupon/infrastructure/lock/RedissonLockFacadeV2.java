package com.qring.coupon.infrastructure.lock;

import com.qring.coupon.application.global.exception.BadRequestException;
import com.qring.coupon.application.v1.res.CouponPostByIdResDTOV1;
import com.qring.coupon.application.v1.service.CouponCacheServiceV1;
import com.qring.coupon.application.v1.service.CouponServiceV1;
import com.qring.coupon.infrastructure.redis.CouponCacheServiceImplV1;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
@Slf4j(topic = "RedissonLockFacadeV2")
public class RedissonLockFacadeV2 {

    private final RedissonClient redissonClient;
    private final CouponServiceV1 couponServiceV1;
    private final CouponCacheServiceV1 couponCacheServiceV1;

    public CouponPostByIdResDTOV1 issueCouponWithLockById(Long userId, Long id, String username) {
        RLock lock = redissonClient.getLock(id.toString());
        boolean available = false;

        try {
            available = lock.tryLock(10, 1, TimeUnit.SECONDS);

            if (!available) {
                log.info("Lock 획득 실패");
                throw new BadRequestException("사용자가 많습니다. 잠시 후 다시 시도해주세요");
            }

            int remainQuantity = couponCacheServiceV1.decreaseCouponQuantity(id);

            couponCacheServiceV1.updateCouponQuantity(id, remainQuantity);

            return couponServiceV1.issueBy(userId, id, username, remainQuantity);

        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            if(available && lock.isHeldByCurrentThread()){
                lock.unlock();
            }
        }
    }
}
