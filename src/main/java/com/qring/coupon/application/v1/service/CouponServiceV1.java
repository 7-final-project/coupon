package com.qring.coupon.application.v1.service;

import com.qring.coupon.application.global.exception.BadRequestException;
import com.qring.coupon.application.global.exception.DuplicateResourceException;
import com.qring.coupon.application.global.exception.UnauthorizedAccessException;
import com.qring.coupon.application.v1.res.CouponGetByIdResDTOV1;
import com.qring.coupon.application.v1.res.CouponPostResDTOV1;
import com.qring.coupon.domain.model.CouponEntity;
import com.qring.coupon.domain.model.constraint.IssuanceStatus;
import com.qring.coupon.domain.repository.CouponRepository;
import com.qring.coupon.infrastructure.util.PassportUtil;
import com.qring.coupon.presentation.v1.req.PostCouponReqDTOV1;
import com.qring.coupon.presentation.v1.req.PutCouponReqDTOV1;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class CouponServiceV1 {

    private final CouponRepository couponRepository;

    @Transactional
    public CouponPostResDTOV1 postBy(String token, PostCouponReqDTOV1 dto) {

        validateRole(token);

        validateCouponNameDuplicate(dto.getCoupon().getName());

        validateCouponDateRange(dto.getCoupon().getOpenAt(), dto.getCoupon().getExpiredAt());

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

        CouponEntity couponEntityForCheck = getCouponEntityById(id);

        return CouponGetByIdResDTOV1.of(couponEntityForCheck);
    }

    @Transactional
    public void putBy(String token, Long id, PutCouponReqDTOV1 dto) {

        CouponEntity couponEntityForCheck = getCouponEntityById(id);

        validateRole(token);

        validateCouponNameDuplicationExcludingCurrentId(id, dto);

        int remainQuantity = getRemainQuantity(dto, couponEntityForCheck);

        String issuanceStatus = getIssuanceStatus(dto, remainQuantity);

        validateCouponDateRange(dto.getCoupon().getOpenAt(), dto.getCoupon().getExpiredAt());

        couponEntityForCheck.modifyCouponEntity(
                dto.getCoupon().getName(),
                dto.getCoupon().getDiscount(),
                dto.getCoupon().getTotalQuantity(),
                remainQuantity,
                dto.getCoupon().getOpenAt(),
                dto.getCoupon().getExpiredAt(),
                dto.getCoupon().getCouponStatus(),
                issuanceStatus,
                PassportUtil.getUsername(token)
        );
    }

    @Transactional
    public void deleteBy(String token, Long id) {

        CouponEntity couponEntityForCheck = getCouponEntityById(id);

        couponEntityForCheck.markAsDelete(PassportUtil.getUsername(token));
        
    }

    // -----
    // NOTE : 쿠폰 존재 여부 검증
    private CouponEntity getCouponEntityById(Long id) {
        return couponRepository.findByIdAndDeletedAtIsNull(id).orElseThrow(
                () -> new BadRequestException("존재하지 않는 쿠폰입니다.")
        );
    }

    // -----
    // NOTE : 관리자 권한 검증
    private void validateRole(String token) {
        if(!PassportUtil.getRole(token).equals("관리자")){
            throw new UnauthorizedAccessException("쿠폰 생성 권한이 없습니다.");
        }
    }

    // -----
    // NOTE : 쿠폰 이름 중복 검증
    private void validateCouponNameDuplicate(String name) {
        if(couponRepository.existsByNameAndDeletedAtIsNull(name)){
            throw new DuplicateResourceException("이미 등록된 쿠폰 이름입니다.");
        }
    }

    // -----
    // NOTE : 현재 쿠폰 이외의 이름 중복 검증
    private void validateCouponNameDuplicationExcludingCurrentId(Long id, PutCouponReqDTOV1 dto) {
        if(couponRepository.existsByNameAndIdNotAndDeletedAtIsNull(dto.getCoupon().getName(), id)){
            throw new DuplicateResourceException("이미 등록된 쿠폰 이름입니다.");
        }
    }
    
    // -----
    // NOTE : 쿠폰 잔여 개수 반환
    private int getRemainQuantity(PutCouponReqDTOV1 dto, CouponEntity couponEntityForCheck) {
        int totalQuantity = couponEntityForCheck.getTotalQuantity();
        int remainQuantity = couponEntityForCheck.getRemainQuantity();

        if (totalQuantity != dto.getCoupon().getTotalQuantity()) {
            int count = dto.getCoupon().getTotalQuantity() - totalQuantity;
            remainQuantity += count;
            if (remainQuantity < 0) {
                throw new BadRequestException("쿠폰 잔여 개수가 부족합니다.");
            }
        }
        return remainQuantity;
    }

    // -----
    // NOTE : 쿠폰 발행 가능 상태 반환
    private String getIssuanceStatus(PutCouponReqDTOV1 dto, int remainQuantity) {
        return remainQuantity == 0 ? IssuanceStatus.Status.CLOSED : dto.getCoupon().getIssuanceStatus();
    }

    // -----
    // NOTE : 쿠폰 날짜 유효성 검증
    private void validateCouponDateRange(LocalDateTime openAt, LocalDateTime expiredAt) {
        if(!openAt.isBefore(expiredAt)){
            throw new BadRequestException("쿠폰의 오픈 날짜는 만료 날짜보다 이전이어야 합니다.");
        }
    }
}
