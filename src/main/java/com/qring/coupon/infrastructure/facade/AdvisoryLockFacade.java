package com.qring.coupon.infrastructure.facade;

import com.qring.coupon.application.v1.res.CouponPostByIdResDTOV1;
import com.qring.coupon.application.v1.service.CouponServiceV1;
import com.qring.coupon.domain.repository.AdvisoryLockRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class AdvisoryLockFacade {
    private final CouponServiceV1 couponServiceV1;
    private final AdvisoryLockRepository advisoryLockRepository;

    // -----
    // NOTE : pg__advisory_xact_lock
    @Transactional
    public CouponPostByIdResDTOV1 issueCouponWithLockById1(Long userId, Long id, String username) {
        advisoryLockRepository.getLockByKey(id);

        return couponServiceV1.issueBy(userId, id, username);
    }

    // -----
    // NOTE : pg_try_advisory_xact_lock
    @Transactional
    public CouponPostByIdResDTOV1 issueCouponWithLockById2(Long userId, Long id, String username) {
        int maxAttempts = 5;
        long retryIntervalMillis = 1000L;

        boolean lockAcquired = advisoryLockRepository.getLockWithTry(id, maxAttempts, retryIntervalMillis);
        if (!lockAcquired) {
            throw new RuntimeException("락 획득 실패: 키 " + id);
        }
        return couponServiceV1.issueBy(userId, id, username);
    }
}
