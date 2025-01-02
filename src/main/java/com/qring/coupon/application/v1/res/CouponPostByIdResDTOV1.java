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

    private UserCoupon userCoupon;

    public static CouponPostByIdResDTOV1 of(UserCouponEntity userCouponEntity) {
        return CouponPostByIdResDTOV1.builder()
                .userCoupon(UserCoupon.from(userCouponEntity))
                .build();
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    private static class UserCoupon {

        private Long id;
        private Long userId;
        private Coupon coupon;

        public static UserCoupon from(UserCouponEntity userCouponEntity) {
            return UserCoupon.builder()
                    .id(userCouponEntity.getId())
                    .userId(userCouponEntity.getUserId())
                    .coupon(Coupon.from(userCouponEntity.getCouponEntity()))
                    .build();
        }
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
