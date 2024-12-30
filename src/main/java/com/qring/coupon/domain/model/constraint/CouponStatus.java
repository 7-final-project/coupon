package com.qring.coupon.domain.model.constraint;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CouponStatus {

    INACTIVE(Status.INACTIVE),
    ACTIVE(Status.ACTIVE),
    CLOSED(Status.CLOSED);

    private final String status;

    public static final class Status {
        public static final String INACTIVE = "비활성";
        public static final String ACTIVE = "활성";
        public static final String CLOSED = "종료";
    }
}
