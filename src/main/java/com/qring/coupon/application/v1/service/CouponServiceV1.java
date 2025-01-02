package com.qring.coupon.application.v1.service;

import com.qring.coupon.application.global.exception.BadRequestException;
import com.qring.coupon.application.global.exception.DuplicateResourceException;
import com.qring.coupon.application.global.exception.UnauthorizedAccessException;
import com.qring.coupon.application.v1.res.CouponGetByIdResDTOV1;
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

        if(!PassportUtil.getRole(token).equals("관리자")){
            throw new UnauthorizedAccessException("쿠폰 생성 권한이 없습니다.");
        }

        if(couponRepository.existsByNameAndDeletedAtIsNull(dto.getCoupon().getName())){
            throw new DuplicateResourceException("이미 등록된 쿠폰 이름입니다.");
        }

        if(!dto.getCoupon().getOpenAt().isBefore(dto.getCoupon().getExpiredAt())){
            throw new BadRequestException("쿠폰의 오픈 날짜는 만료 날짜보다 이전이어야 합니다.");
        }

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

    @Transactional(readOnly = true)
    public CouponGetByIdResDTOV1 getBy(Long id) {

        CouponEntity couponEntityForCheck = couponRepository.findByIdAndDeletedAtIsNull(id).orElseThrow(
                () -> new BadRequestException("존재하지 않는 쿠폰입니다.")
        );

        return CouponGetByIdResDTOV1.of(couponEntityForCheck);
    }
}
