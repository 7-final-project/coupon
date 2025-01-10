package com.qring.coupon.infrastructure.scheduler;

import com.qring.coupon.application.global.exception.EntityNotFoundException;
import com.qring.coupon.domain.model.CouponEntity;
import com.qring.coupon.domain.model.constraint.CouponStatus;
import com.qring.coupon.domain.model.constraint.IssuanceStatus;
import com.qring.coupon.domain.repository.CouponRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Component;
import org.springframework.transaction.support.TransactionTemplate;

import java.time.ZoneId;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class CouponSchedulerImpl implements CouponScheduler {

    private final TaskScheduler taskScheduler;
    private final TransactionTemplate transactionTemplate;
    private final CouponRepository couponRepository;

    public void scheduleIssuanceStatus(CouponEntity couponEntity) {
        // NOTE : 쿠폰 활성화 스케줄
        taskScheduler.schedule(() -> {
            openCouponById(couponEntity.getId());
        }, Date.from(couponEntity.getOpenAt().atZone(ZoneId.systemDefault()).toInstant()));

        // NOTE : 쿠폰 비활성화 스케줄
        taskScheduler.schedule(() -> {
            closedCouponById(couponEntity.getId());
        }, Date.from(couponEntity.getExpiredAt().atZone(ZoneId.systemDefault()).toInstant()));
    }

    private void openCouponById(Long id) {
        transactionTemplate.execute(status -> {
            CouponEntity couponEntityForMapping = getCouponEntityById(id);
            couponEntityForMapping.modifyCouponStatus(IssuanceStatus.OPEN, CouponStatus.ACTIVE);
            return null;
        });
    }

    private void closedCouponById(Long id) {
        transactionTemplate.execute(status -> {
            CouponEntity couponEntityForMapping = getCouponEntityById(id);
            couponEntityForMapping.modifyCouponStatus(IssuanceStatus.CLOSED, CouponStatus.EXPIRED);
            return null;
        });
    }

    private CouponEntity getCouponEntityById(Long id) {
        return couponRepository.findByIdAndDeletedAtIsNull(id).orElseThrow(
                () -> new EntityNotFoundException("존재하지 않는 쿠폰입니다.")
        );
    }

}
