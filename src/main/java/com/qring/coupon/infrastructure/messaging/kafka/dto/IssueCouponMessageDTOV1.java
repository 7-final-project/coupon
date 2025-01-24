package com.qring.coupon.infrastructure.messaging.kafka.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IssueCouponMessageDTOV1 {

    private Long userId;
    private String username;
    private Long couponId;

    public static IssueCouponMessageDTOV1 from(Long userId, String username, Long id) {
        return IssueCouponMessageDTOV1.builder()
                .userId(userId)
                .username(username)
                .couponId(id)
                .build();
    }
}
