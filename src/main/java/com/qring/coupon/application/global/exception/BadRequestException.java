package com.qring.coupon.application.global.exception;

import lombok.Getter;

@Getter
public class BadRequestException extends CouponException {
    public BadRequestException(String message) {
        super(ErrorCode.BAD_REQUEST_ERROR, message);
    }
}
