package com.qring.coupon.presentation.v1.req;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class PutCouponReqDTOV1 {

    @Valid
    @NotNull(message = "쿠폰 정보를 입력해주세요.")
    private Coupon coupon;

    @Getter
    private static class Coupon {

        @NotBlank(message = "쿠폰 이름을 입력해주세요.")
        private String name;

        @Positive(message = "쿠폰 할인 금액을 입력해주세요.")
        private int discount;

        @Positive(message = "쿠폰 전체 개수를 입력해주세요.")
        private int totalQuantity;

        @NotNull(message = "쿠폰 오픈 시간을 입력해주세요.")
        private LocalDateTime openAt;

        @NotNull(message = "쿠폰 만료 일자를 입력해주세요.")
        private LocalDateTime expiredAt;

    }

}
