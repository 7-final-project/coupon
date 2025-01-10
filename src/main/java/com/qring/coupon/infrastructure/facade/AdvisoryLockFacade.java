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

    @Transactional
    public CouponPostByIdResDTOV1 issueCouponWithLockById(Long userId, Long id, String username) {
        if (!advisoryLockRepository.getLockByKey(id)) {
            throw new RuntimeException("락 획득 실패");
        }
        CouponPostByIdResDTOV1 couponPostByIdResDTOV1 = null;
        try {
            couponPostByIdResDTOV1 = couponServiceV1.issueBy(userId, id, username);
        } finally {
            advisoryLockRepository.releaseLockByKey(id);
        }
        return couponPostByIdResDTOV1;
    }
}
