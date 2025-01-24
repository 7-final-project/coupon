package com.qring.coupon.domain.model.constraint;

import com.qring.coupon.application.global.exception.BadRequestException;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Objects;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class IssuanceCheck {

    private static final String SUCCESS = "success";
    private static final String FAILURE = "failure";

    public static void checkResult (String result) {
        if (Objects.equals(FAILURE, result)) {
            throw new BadRequestException("쿠폰 발급에 실패하였습니다.");
        }
    }
}
