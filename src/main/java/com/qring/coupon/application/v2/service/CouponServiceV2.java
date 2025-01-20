package com.qring.coupon.application.v2.service;

import com.qring.coupon.application.global.exception.BadRequestException;
import com.qring.coupon.application.global.exception.DuplicateResourceException;
import com.qring.coupon.application.global.exception.EntityNotFoundException;
import com.qring.coupon.application.v1.res.CouponPostByIdResDTOV1;
import com.qring.coupon.domain.model.CouponEntity;
import com.qring.coupon.domain.model.UserCouponEntity;
import com.qring.coupon.domain.repository.CouponRepository;
import com.qring.coupon.domain.repository.UserCouponRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class CouponServiceV2 {

    private final CouponRepository couponRepository;
    private final UserCouponRepository userCouponRepository;

    @Transactional
    public CouponPostByIdResDTOV1 issueCouponByIdWithPessimisticLock(Long userId, Long id, String username) {

        if (userCouponRepository.existsByUserIdAndCouponEntityId(userId, id)) {
            throw new DuplicateResourceException("이미 보유하고 있는 쿠폰입니다.");
        }

        CouponEntity couponEntityForCheck = getCouponEntityById(id);

        couponEntityForCheck.decreaseRemainingQuantity();

        if (!Objects.equals(couponEntityForCheck.getIssuanceStatus().getStatus(), "개시")) {
            throw new BadRequestException("해당 쿠폰은 발급이 불가능합니다.");
        }

        saveUserCouponEntity(userId, username, couponEntityForCheck);

        return CouponPostByIdResDTOV1.of(couponEntityForCheck);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void saveUserCouponEntity(Long userId, String username, CouponEntity couponEntityForCheck) {
        UserCouponEntity userCouponEntityForSave = UserCouponEntity.createUserCouponEntity(couponEntityForCheck, userId, username);
        userCouponRepository.save(userCouponEntityForSave);
    }

    // -----
    // NOTE : 쿠폰 존재 여부 검증
    private CouponEntity getCouponEntityById(Long id) {
        return couponRepository.findByIdAndDeletedAtIsNull(id)
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 쿠폰입니다.")
                );
    }
}
