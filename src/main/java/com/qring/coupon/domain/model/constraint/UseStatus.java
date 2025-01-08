package com.qring.coupon.domain.model.constraint;

import com.qring.coupon.application.global.exception.BadRequestException;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UseStatus {

    USED(Status.USED),
    UNUSED(Status.UNUSED);

    private final String status;

    public static class Status {
        public static final String USED = "사용완료";
        public static final String UNUSED = "미사용";
    }

    public static UseStatus fromString(String status) {
        return switch (status) {
            case "USED" -> UseStatus.USED;
            case "UNUSED" -> UseStatus.UNUSED;
            default -> throw new BadRequestException("유효하지 않은 쿠폰 상태입니다." + status);
        };
    }
}
