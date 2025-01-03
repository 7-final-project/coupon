package com.qring.coupon.application.global.exception;

import lombok.Getter;

@Getter
public class EntityNotFoundException extends CouponException {
    public EntityNotFoundException(String message) {
        super(ErrorCode.NOT_FOUND_ERROR, message);
    }
}
