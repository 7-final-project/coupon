package com.qring.coupon.application.v1.service;

import com.qring.coupon.application.global.exception.BadRequestException;
import com.qring.coupon.application.global.exception.DuplicateResourceException;
import com.qring.coupon.application.global.exception.EntityNotFoundException;
import com.qring.coupon.application.global.exception.UnauthorizedAccessException;
import com.qring.coupon.application.v1.res.*;
import com.qring.coupon.domain.model.CouponEntity;
import com.qring.coupon.domain.model.UserCouponEntity;
import com.qring.coupon.domain.model.constraint.IssuanceStatus;
import com.qring.coupon.domain.repository.CouponRepository;
import com.qring.coupon.domain.repository.UserCouponRepository;
import com.qring.coupon.infrastructure.scheduler.CouponScheduler;
import com.qring.coupon.infrastructure.util.PassportUtil;
import com.qring.coupon.presentation.v1.req.PostCouponReqDTOV1;
import com.qring.coupon.presentation.v1.req.PutCouponReqDTOV1;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class CouponServiceV1 {

    private final CouponRepository couponRepository;
    private final UserCouponRepository userCouponRepository;
    private final CouponScheduler couponScheduler;

    @Transactional
    public CouponPostResDTOV1 postBy(String passport, PostCouponReqDTOV1 dto) {

        validateCouponCreationProcess(passport, dto);

        CouponEntity couponEntityForSave = CouponEntity.createCouponEntity(
                dto.getCoupon().getName(),
                dto.getCoupon().getDiscount(),
                dto.getCoupon().getTotalQuantity(),
                dto.getCoupon().getOpenAt(),
                dto.getCoupon().getExpiredAt(),
                PassportUtil.getUsername(passport)
        );

        couponScheduler.scheduleIssuanceStatus(couponEntityForSave);

        return CouponPostResDTOV1.of(couponRepository.save(couponEntityForSave));
    }

    @Transactional
    public CouponPostByIdResDTOV1 issueBy(String passport, Long id) {

        CouponEntity couponEntityForCheck = getCouponEntityById(id);

        if (couponEntityForCheck.getRemainingQuantity() <= 0) {
            throw new BadRequestException("쿠폰이 매진되었습니다.");
        }

        if (userCouponRepository.existsByUserIdAndCouponEntity(PassportUtil.getUserId(passport), couponEntityForCheck)) {
            throw new DuplicateResourceException("이미 보유하고 있는 쿠폰입니다.");
        }

        if (!Objects.equals(couponEntityForCheck.getIssuanceStatus().getStatus(), "개시")) {
            throw new BadRequestException("해당 쿠폰은 발급이 불가능합니다.");
        }

        UserCouponEntity userCouponEntityForSave = UserCouponEntity.createUserCouponEntity(couponEntityForCheck, PassportUtil.getUserId(passport), PassportUtil.getUsername(passport));
        userCouponRepository.save(userCouponEntityForSave);

        return CouponPostByIdResDTOV1.of(couponEntityForCheck);
    }

    @Transactional(readOnly = true)
    public CouponSearchResDTOV1 searchBy(Pageable pageable, Long userId, String name, String couponStatus, String issuanceStatus, String sort) {
        return CouponSearchResDTOV1.of(couponRepository.couponEntityPageByDeletedAtIsNullWithConditions(pageable, userId, name, couponStatus, issuanceStatus, sort));
    }

    @Transactional(readOnly = true)
    public CouponTableGetByUserIdResDTOV1 getBy(String passport) {

        Set<UserCouponEntity> findUserCouponSetForMapping = userCouponRepository.findUserCouponEntitySetFetchJoinCouponByUserIdAndDeletedAtIsNull(PassportUtil.getUserId(passport));

        return CouponTableGetByUserIdResDTOV1.of(findUserCouponSetForMapping);
    }

    @Transactional(readOnly = true)
    public CouponGetByIdResDTOV1 getBy(Long id) {

        CouponEntity couponEntityForMapping = getCouponEntityById(id);

        return CouponGetByIdResDTOV1.of(couponEntityForMapping);
    }

    @Transactional
    public void putBy(String passport, Long id, PutCouponReqDTOV1 dto) {

        CouponEntity couponEntityForModification = getCouponEntityById(id);

        validateUserRole(passport);

        validateCouponNameDuplicate(id, dto.getCoupon().getName());

        int remainingQuantity = calculateRemainingQuantity(dto.getCoupon().getTotalQuantity(), couponEntityForModification);

        String issuanceStatus = getIssuanceStatusByRemainingQuantity(dto.getCoupon().getIssuanceStatus(), remainingQuantity);

        validateCouponDateRange(dto.getCoupon().getOpenAt(), dto.getCoupon().getExpiredAt());

        couponEntityForModification.modifyCouponEntity(
                dto.getCoupon().getName(),
                dto.getCoupon().getDiscount(),
                dto.getCoupon().getTotalQuantity(),
                remainingQuantity,
                dto.getCoupon().getOpenAt(),
                dto.getCoupon().getExpiredAt(),
                dto.getCoupon().getCouponStatus(),
                issuanceStatus,
                PassportUtil.getUsername(passport)
        );

        couponScheduler.scheduleIssuanceStatus(couponEntityForModification);
    }

    @Transactional
    public void deleteBy(String passport, Long id) {

        CouponEntity couponEntityForDeletion = getCouponEntityById(id);

        couponEntityForDeletion.deletedCouponEntity(PassportUtil.getUsername(passport));

    }

    // -----
    // NOTE : 쿠폰 생성 검증 프로세스
    private void validateCouponCreationProcess(String passport, PostCouponReqDTOV1 dto) {
        validateUserRole(passport);

        validateCouponNameDuplicate(dto.getCoupon().getName());

        validateCouponDateRange(dto.getCoupon().getOpenAt(), dto.getCoupon().getExpiredAt());
    }

    // -----
    // NOTE : 쿠폰 존재 여부 검증
    private CouponEntity getCouponEntityById(Long id) {
        return couponRepository.findByIdAndDeletedAtIsNull(id).orElseThrow(
                () -> new EntityNotFoundException("존재하지 않는 쿠폰입니다.")
        );
    }

    // -----
    // NOTE : 관리자 권한 검증
    private void validateUserRole(String passport) {
        if (!Objects.equals(PassportUtil.getRole(passport), "관리자")) {
            throw new UnauthorizedAccessException("쿠폰 생성 권한이 없습니다.");
        }
    }

    // -----
    // NOTE : 쿠폰 이름 중복 검증
    private void validateCouponNameDuplicate(String name) {
        if (couponRepository.existsByNameAndDeletedAtIsNull(name)) {
            throw new DuplicateResourceException("이미 등록된 쿠폰 이름입니다.");
        }
    }

    // -----
    // NOTE : 현재 쿠폰 이외의 이름 중복 검증
    private void validateCouponNameDuplicate(Long id, String name) {
        if (couponRepository.existsByNameAndIdNotAndDeletedAtIsNull(name, id)) {
            throw new DuplicateResourceException("이미 등록된 쿠폰 이름입니다.");
        }
    }

    // -----
    // NOTE : 쿠폰 잔여 개수 반환
    private int calculateRemainingQuantity(int reqTotalQuantity, CouponEntity couponEntityForModification) {
        int totalQuantity = couponEntityForModification.getTotalQuantity();
        int remainingQuantity = couponEntityForModification.getRemainingQuantity();

        if (totalQuantity != reqTotalQuantity) {
            int count = reqTotalQuantity - totalQuantity;
            remainingQuantity += count;
            if (remainingQuantity < 0) {
                throw new BadRequestException("쿠폰 잔여 개수가 부족합니다.");
            }
        }
        return remainingQuantity;
    }

    // -----
    // NOTE : 쿠폰 발행 가능 상태 반환
    private String getIssuanceStatusByRemainingQuantity(String issuanceStatus, int remainingQuantity) {
        return remainingQuantity == 0 ? IssuanceStatus.Status.CLOSED : issuanceStatus;
    }

    // -----
    // NOTE : 쿠폰 날짜 유효성 검증
    private void validateCouponDateRange(LocalDateTime openAt, LocalDateTime expiredAt) {
        if (!openAt.isBefore(expiredAt)) {
            throw new BadRequestException("쿠폰의 오픈 날짜는 만료 날짜보다 이전이어야 합니다.");
        }
    }
}
