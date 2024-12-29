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
public class CouponPostResDTOV1 {

    private CouponDTO couponDTO;

    public static CouponPostResDTOV1 of(CouponEntity couponEntity) {
        return CouponPostResDTOV1.builder()
                .couponDTO(CouponDTO.from(couponEntity))
                .build();
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    private static class CouponDTO{
        private Long id;
        private String name;
        private int totalQuantity;
        private int remainQuantity;
        private LocalDateTime openAt;
        private LocalDateTime expireAt;
        private StockStatus stockStatus;
        private CouponStatus couponStatus;

        public static CouponDTO from(CouponEntity couponEntity){
            return CouponDTO.builder()
                    .id(couponEntity.getId())
                    .name(couponEntity.getName())
                    .totalQuantity(couponEntity.getTotalQuantity())
                    .remainQuantity(couponEntity.getRemainQuantity())
                    .openAt(couponEntity.getOpenAt())
                    .expireAt(couponEntity.getExpiredAt())
                    .stockStatus(couponEntity.getStockStatus())
                    .couponStatus(couponEntity.getCouponStatus())
                    .build();
        }
    }

}
