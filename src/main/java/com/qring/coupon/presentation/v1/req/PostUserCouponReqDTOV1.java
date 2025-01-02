package com.qring.coupon.presentation.v1.req;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostUserCouponReqDTOV1 {

    @Valid
    @NotNull(message = "발급할 쿠폰 정보를 입력해주세요.")
    private UserCoupon userCoupon;

    @Getter
    private static class UserCoupon {

        @NotNull(message = "쿠폰 아이디를 입력해주세요.")
        private Long couponId;

    }

}
