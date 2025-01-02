package com.qring.coupon.application.v1.res;

import com.qring.coupon.domain.model.CouponEntity;
import com.qring.coupon.domain.model.UserCouponEntity;
import com.qring.coupon.domain.model.constraint.CouponStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CouponPostByIdResDTOV1 {

    private Coupon coupon;

    public static CouponPostByIdResDTOV1 of(CouponEntity couponEntity) {
        return CouponPostByIdResDTOV1.builder()
                .coupon(Coupon.from(couponEntity))
                .build();
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    private static class Coupon {

        private Long id;
        private String name;
        private int discount;
        private LocalDateTime openAt;
        private LocalDateTime expiredAt;
        private CouponStatus couponStatus;

        public static Coupon from(CouponEntity couponEntity) {
            return Coupon.builder()
                    .id(couponEntity.getId())
                    .name(couponEntity.getName())
                    .discount(couponEntity.getDiscount())
                    .openAt(couponEntity.getOpenAt())
                    .expiredAt(couponEntity.getExpiredAt())
                    .couponStatus(couponEntity.getCouponStatus())
                    .build();
        }
    }
}
