package com.qring.coupon.application.v2.service;

import com.qring.coupon.application.global.exception.BadRequestException;
import com.qring.coupon.application.global.exception.EntityNotFoundException;
import com.qring.coupon.application.v1.res.CouponPostByIdResDTOV1;
import com.qring.coupon.application.v2.messaging.KafkaMessageProducerV1;
import com.qring.coupon.application.v2.messaging.RedisService;
import com.qring.coupon.domain.model.CouponEntity;
import com.qring.coupon.domain.model.UserCouponEntity;
import com.qring.coupon.domain.repository.CouponRepository;
import com.qring.coupon.domain.repository.UserCouponRepository;
import com.qring.coupon.infrastructure.messaging.kafka.dto.IssueCouponMessageDTOV1;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class CouponServiceV2 {

    private final CouponRepository couponRepository;
    private final UserCouponRepository userCouponRepository;
    private final RedisService redisService;
    private final KafkaMessageProducerV1 kafkaMessageProducerV1;

    @Transactional
    public CouponPostByIdResDTOV1 issueBy(Long userId, String username, Long id) {

        CouponEntity couponEntityForCheck = getCouponEntityById(id);

        validateIssuanceStatus(couponEntityForCheck);

        redisService.saveCoupon(userId, couponEntityForCheck);

        kafkaMessageProducerV1.publishCouponIssuanceMessage(IssueCouponMessageDTOV1.from(userId, username, id));

        return CouponPostByIdResDTOV1.of(couponEntityForCheck);
    }

    @Transactional
    public void saveUserCoupon(IssueCouponMessageDTOV1 message) {

        CouponEntity couponEntityByIdWithLock = getCouponEntityById(message.getCouponId());

        UserCouponEntity userCouponEntityForSave = UserCouponEntity.createUserCouponEntity(couponEntityByIdWithLock, message.getUserId(), message.getUsername());
        userCouponRepository.save(userCouponEntityForSave);

    }

    // -----
    // NOTE : 쿠폰 발급 상태 검증 프로세스
    private static void validateIssuanceStatus(CouponEntity couponEntityForCheck) {
        if (!Objects.equals(couponEntityForCheck.getIssuanceStatus().getStatus(), "개시")) {
            throw new BadRequestException("해당 쿠폰은 발급이 불가능합니다.");
        }
    }

    // -----
    // NOTE : 쿠폰 존재 여부 검증
    private CouponEntity getCouponEntityById(Long id) {
        return couponRepository.findByIdAndDeletedAtIsNull(id).orElseThrow(
                () -> new EntityNotFoundException("존재하지 않는 쿠폰입니다.")
        );
    }
}
