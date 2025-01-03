package com.qring.coupon.domain.model.constraint;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CouponStatus {

    INACTIVE(Status.INACTIVE),
    ACTIVE(Status.ACTIVE),
    EXPIRED(Status.EXPIRED);

    private final String status;

    public static final class Status {
        public static final String INACTIVE = "비활성";
        public static final String ACTIVE = "활성";
        public static final String EXPIRED = "만료";
    }

    public static CouponStatus fromString(String status) {
        return switch (status) {
            case Status.INACTIVE -> CouponStatus.INACTIVE;
            case Status.ACTIVE -> CouponStatus.ACTIVE;
            case Status.EXPIRED -> CouponStatus.EXPIRED;
            default -> throw new IllegalArgumentException("유효하지 않은 쿠폰 상태입니다: " + status);
        };
    }
}
