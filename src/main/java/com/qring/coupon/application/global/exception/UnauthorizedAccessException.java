package com.qring.coupon.application.global.exception;

import lombok.Getter;

@Getter
public class UnauthorizedAccessException extends CouponException {
    public UnauthorizedAccessException(String message) {
        super(ErrorCode.AUTHORITY_ERROR, message);
    }
}
