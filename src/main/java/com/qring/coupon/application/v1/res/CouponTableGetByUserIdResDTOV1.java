package com.qring.coupon.application.v1.res;

import com.qring.coupon.domain.model.CouponEntity;
import com.qring.coupon.domain.model.UserCouponEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CouponTableGetByUserIdResDTOV1 {

    private Set<UserCoupon> userCouponSet;

    public static CouponTableGetByUserIdResDTOV1 of(Set<UserCouponEntity> userCouponEntitySet) {
        return CouponTableGetByUserIdResDTOV1.builder()
                .userCouponSet(UserCoupon.from(userCouponEntitySet))
                .build();
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UserCoupon {

        private String useStatus;
        private Coupon coupon;

        public static Set<UserCoupon> from(Set<UserCouponEntity> userCouponEntitySet) {
            return userCouponEntitySet.stream()
                    .map(UserCoupon::from)
                    .collect(Collectors.toSet());
        }

        public static UserCoupon from(UserCouponEntity userCouponEntity) {
            return UserCoupon.builder()
                    .useStatus(userCouponEntity.getUseStatus().getStatus())
                    .coupon(Coupon.from(userCouponEntity.getCouponEntity()))
                    .build();
        }

        @Getter
        @Builder
        @NoArgsConstructor
        @AllArgsConstructor
        public static class Coupon {

            private Long id;
            private String name;
            private LocalDateTime openAt;
            private LocalDateTime expiredAt;
            private String couponStatus;
            private String issuanceStatus;

            public static Coupon from(CouponEntity couponEntity) {
                return Coupon.builder()
                        .id(couponEntity.getId())
                        .name(couponEntity.getName())
                        .openAt(couponEntity.getOpenAt())
                        .expiredAt(couponEntity.getExpiredAt())
                        .couponStatus(couponEntity.getCouponStatus().getStatus())
                        .issuanceStatus(couponEntity.getIssuanceStatus().getStatus())
                        .build();
            }
        }
    }
}
