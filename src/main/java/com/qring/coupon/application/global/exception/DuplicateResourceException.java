package com.qring.coupon.application.global.exception;

import lombok.Getter;

@Getter
public class DuplicateResourceException extends CouponException {
    public DuplicateResourceException(String message) {
        super(ErrorCode.DUPLICATE_ERROR, message);
    }
}
