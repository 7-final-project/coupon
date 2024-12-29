package com.qring.coupon.application.v1.res;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CouponDeleteResDTOV1 {

    private Coupon coupon;

    public static CouponDeleteResDTOV1 of(Long id) {
        return CouponDeleteResDTOV1.builder()
                .coupon(Coupon.from(id))
                .build();
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    private static class Coupon{
        private Long id;

        public static Coupon from(Long id){
            return Coupon.builder()
                    .id(id)
                    .build();

        }
    }

}
