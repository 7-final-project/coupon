package com.qring.coupon.domain.model.constraint;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum StockStatus {
    IN_STOCK(Status.IN_STOCK), // 재고있음
    OUT_OF_STOCK(Status.OUT_OF_STOCK); // 품절

    private final String status;

    public static class Status {
        public static final String IN_STOCK = "품절";
        public static final String OUT_OF_STOCK = "재고있음";
    }
}
