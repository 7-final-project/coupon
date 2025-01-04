package com.qring.coupon.application.global.exception;

import lombok.Getter;

@Getter
public class CouponException extends RuntimeException {

    private final ErrorCode errorCode;
    private final String message;

    public CouponException(ErrorCode errorCode, String message) {
        this.errorCode = errorCode;
        this.message = message;
    }

}
