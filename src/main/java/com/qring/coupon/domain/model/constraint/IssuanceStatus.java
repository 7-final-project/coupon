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
}
