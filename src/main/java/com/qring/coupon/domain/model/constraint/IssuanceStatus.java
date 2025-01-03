package com.qring.coupon.domain.model.constraint;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum IssuanceStatus {

    OPEN(Status.OPEN),
    CLOSED(Status.CLOSED);

    private final String status;

    public static final class Status {
        public static final String OPEN = "개시";
        public static final String CLOSED = "종료";
    }

    public static IssuanceStatus fromString(String status) {
        return switch (status) {
            case Status.OPEN -> IssuanceStatus.OPEN;
            case Status.CLOSED -> IssuanceStatus.CLOSED;
            default -> throw new IllegalArgumentException("유효하지 않은 쿠폰 발급 상태입니다." + status);
        };
    }
}
