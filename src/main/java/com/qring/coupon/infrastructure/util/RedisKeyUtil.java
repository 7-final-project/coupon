package com.qring.coupon.infrastructure.util;

public class RedisKeyUtil {

    public static final String COUPON_ISSUE_REDIS_KEY_PREFIX = "couponIssue:";

    public static String getCouponIssueKey(Long id) {
        return COUPON_ISSUE_REDIS_KEY_PREFIX + id;
    }
}
