package com.qring.coupon.application.v1.res;

import com.qring.coupon.domain.model.UserCouponEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserCouponPostResDTOV1 {

    private UserCoupon userCoupon;

    public static UserCouponPostResDTOV1 of(UserCouponEntity userCouponEntity) {
        return UserCouponPostResDTOV1.builder()
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
        private Long couponId;

        public static UserCoupon from(UserCouponEntity userCouponEntity) {
            return UserCoupon.builder()
                    .id(userCouponEntity.getId())
                    .userId(userCouponEntity.getUserId())
                    .couponId(userCouponEntity.getId())
                    .build();
        }

    }

}
