//package com.qring.coupon.infrastructure.lock;
//
//import com.qring.coupon.application.v1.res.CouponPostByIdResDTOV1;
//import com.qring.coupon.application.v1.service.CouponServiceV1;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.redisson.api.RLock;
//import org.redisson.api.RedissonClient;
//import org.springframework.stereotype.Component;
//
//import java.util.concurrent.TimeUnit;
//
//@Slf4j(topic = "redisson-lock")
//@Component
//@RequiredArgsConstructor
//public class RedissonLockFacadeV1 {
//
//    private final RedissonClient redissonClient;
//
//    private final CouponServiceV1 couponServiceV1;
//
//    public CouponPostByIdResDTOV1 issueCouponWithLockById(Long userId, Long id, String username) {
//        RLock lock = redissonClient.getLock(id.toString());
//
//        try {
//            boolean available = lock.tryLock(10, 1, TimeUnit.SECONDS);
//
//            if (!available) {
//                log.info("Lock 획득 실패");
//            }
//
//            return couponServiceV1.issueBy(userId, id, username);
//
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        } finally {
//            lock.unlock();
//        }
//    }
//}
