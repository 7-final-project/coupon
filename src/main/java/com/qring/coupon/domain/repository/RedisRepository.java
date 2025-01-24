package com.qring.coupon.domain.repository;

public interface RedisRepository {

    void issueRequest(Long userId, Long id, int totalQuantity);

}
