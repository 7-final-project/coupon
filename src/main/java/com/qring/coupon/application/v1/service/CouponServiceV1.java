package com.qring.coupon.application.v1.service;

import com.qring.coupon.application.global.exception.BadRequestException;
import com.qring.coupon.application.global.exception.DuplicateResourceException;
import com.qring.coupon.application.global.exception.UnauthorizedAccessException;
import com.qring.coupon.application.v1.res.CouponPostResDTOV1;
import com.qring.coupon.domain.model.CouponEntity;
import com.qring.coupon.domain.repository.CouponRepository;
import com.qring.coupon.infrastructure.util.PassportUtil;
import com.qring.coupon.presentation.v1.req.PostCouponReqDTOV1;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CouponServiceV1 {

    private final CouponRepository couponRepository;

    @Transactional
    public CouponPostResDTOV1 postBy(String token, PostCouponReqDTOV1 dto) {

        CouponEntity couponEntityForSave = CouponEntity.createCouponEntity(
                dto.getCoupon().getName(),
                dto.getCoupon().getDiscount(),
                dto.getCoupon().getTotalQuantity(),
                dto.getCoupon().getOpenAt(),
                dto.getCoupon().getExpiredAt(),
                PassportUtil.getUsername(token)
        );

        return CouponPostResDTOV1.of(couponRepository.save(couponEntityForSave));
    }
}
