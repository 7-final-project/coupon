package com.qring.coupon.infrastructure.lock;

import com.qring.coupon.application.global.exception.BadRequestException;
import com.qring.coupon.application.v1.res.CouponPostByIdResDTOV1;
import com.qring.coupon.application.v1.service.CouponServiceV1;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j(topic = "OptimisticLockFacade")
public class OptimisticLockFacade {

    private final CouponServiceV1 couponServiceV1;

    public CouponPostByIdResDTOV1 issueCouponByIdWithOptimisticLock(Long userId, Long id, String username) {
        int retryCount = 5; // 최대 재시도 횟수
        while (retryCount-- > 0) {
            try {
                return couponServiceV1.issueBy(userId, id, username);
            } catch (IllegalArgumentException e) {
                throw e;
            } catch (Exception e) {
                log.warn("Version 불일치 발생! : {}", e.getMessage());
                try {
                    Thread.sleep(50); // 재시도 전 잠시 대기
                } catch (InterruptedException interruptedException) {
                    Thread.currentThread().interrupt();
                    throw new RuntimeException("스레드 실행이 중단되었습니다.", interruptedException);
                }
            }
        }
        throw new BadRequestException("쿠폰 발급 재시도 최대 횟수를 초과했습니다.");
    }
}