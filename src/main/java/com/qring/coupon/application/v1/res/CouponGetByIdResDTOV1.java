package com.qring.coupon.application.v1.res;

import com.qring.coupon.domain.model.CouponEntity;
import com.qring.coupon.domain.model.constraint.CouponStatus;
import com.qring.coupon.domain.model.constraint.StockStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CouponGetByIdResDTOV1 {

    private Coupon coupon;

    public static CouponGetByIdResDTOV1 of(CouponEntity couponEntity) {
        return CouponGetByIdResDTOV1.builder()
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
        private int totalQuantity;
        private int remainQuantity;
        private LocalDateTime openAt;
        private LocalDateTime expiredAt;
        private CouponStatus couponStatus;

        public static Coupon from(CouponEntity couponEntity){
            return Coupon.builder()
                    .id(couponEntity.getId())
                    .name(couponEntity.getName())
                    .totalQuantity(couponEntity.getTotalQuantity())
                    .remainQuantity(couponEntity.getRemainQuantity())
                    .openAt(couponEntity.getOpenAt())
                    .expiredAt(couponEntity.getExpiredAt())
                    .couponStatus(couponEntity.getCouponStatus())
                    .build();
        }
    }

}
