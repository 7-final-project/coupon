package com.qring.coupon.presentation.v1.req;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class PostCouponReqDTOV1 {

    @Valid
    @NotNull(message = "쿠폰 정보를 입력해주세요.")
    private CouponDTO couponDTO;


    @Getter
    private static class CouponDTO {

        @NotBlank(message = "쿠폰 이름을 입력해주세요.")
        private String name;

        private int totalQuantity;

        private int remainQuantity;

        @NotNull(message = "쿠폰 오픈 시간을 입력해주세요.")
        private LocalDateTime openAt;

        @NotNull(message = "쿠폰 만료 일자를 입력해주세요.")
        private LocalDateTime expiredAt;
    }

}
